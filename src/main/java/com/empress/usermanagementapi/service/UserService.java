package com.empress.usermanagementapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.empress.usermanagementapi.repository.UserRepository;
import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.entity.Role;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Check if an email is already in use.
     */
    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }

    /**
     * Check if a username is already in use.
     */
    public boolean usernameExists(String username) {
        return userRepo.existsByUsername(username);
    }

    /**
     * Create a new user (caller is responsible for checking duplicates first).
     */
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        return userRepo.save(user);
    }

    // ← below this line, the original file’s other methods follow, unchanged

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public User update(User user) {
        return userRepo.save(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }
}
