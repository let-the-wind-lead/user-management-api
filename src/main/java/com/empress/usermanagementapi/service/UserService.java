package com.empress.usermanagementapi.service;

import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> listAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public User create(User user) {
        return userRepo.save(user);
    }

    public User update(Long id, User user) {
        User existing = userRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No user " + id));
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setRole(user.getRole());
        return userRepo.save(existing);
    }

    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new IllegalArgumentException("No user " + id);
        }
        userRepo.deleteById(id);
    }
}
