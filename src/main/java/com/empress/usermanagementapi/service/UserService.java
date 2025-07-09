package com.empress.usermanagementapi.service;

import com.empress.usermanagementapi.entity.Role;
import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a brand‐new user: always bcrypt‐encode the raw password
     * and default the role to USER if none is provided.
     */
    public User create(User user) {
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        return userRepo.save(user);
    }

    /**
     * Update an existing user: if a non‐empty password is provided,
     * bcrypt‐encode and save; otherwise leave the existing password intact.
     */
    public User update(Long id, User user) {
        User existing = userRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No user " + id));
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setRole(user.getRole());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepo.save(existing);
    }

    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new IllegalArgumentException("No user " + id);
        }
        userRepo.deleteById(id);
    }
}
