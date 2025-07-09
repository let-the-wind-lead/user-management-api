package com.empress.usermanagementapi.controller;

import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.UserRepository;
import com.empress.usermanagementapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    /**
     * POST /forgot-password
     * {
     *   "email": "user@example.com"
     * }
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        Optional<User> maybeUser = userRepository.findByEmail(email);

        if (maybeUser.isEmpty()) {
            return ResponseEntity
                .badRequest()
                .body("No account found with that email address.");
        }

        User user = maybeUser.get();
        // Generate a one-time token (in a real app you’d persist this with an expiry)
        String token = UUID.randomUUID().toString();

        // Send the reset link via email
        emailService.sendPasswordResetEmail(user.getEmail(), token);

        return ResponseEntity.ok("Password reset email sent.");
    }
}
