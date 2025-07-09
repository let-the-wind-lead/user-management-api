package com.empress.usermanagementapi.controller;

import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // 1. List all users (ADMIN only)
    @GetMapping
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // 2. Create a new user (ADMIN only)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // (Optional) encode password here if using NoOp remove or adjust
        User saved = userRepo.save(user);
        return ResponseEntity.created(URI.create("/users/" + saved.getId()))
                             .body(saved);
    }

    // 3. Update a user (ADMIN only)
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User user) {
        Optional<User> existing = userRepo.findById(id);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User u = existing.get();
        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());
        u.setRole(user.getRole());
        // (Optional) update password: u.setPassword(user.getPassword());
        User saved = userRepo.save(u);
        return ResponseEntity.ok(saved);
    }

    // 4. Delete a user (ADMIN only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
