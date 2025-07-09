package com.empress.usermanagementapi.controller;

import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.UserRepository;
import com.empress.usermanagementapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    /**
     * POST /forgot-password
     * { "email": "user@example.com" }
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity
                .badRequest()
                .body("No account found with that email address.");
        }

        // Generate a one-time token (you should store it in DB with expiry in a real app)
        String token = UUID.randomUUID().toString();

        // Send the reset link via email
        emailService.sendPasswordResetEmail(email, token);

        return ResponseEntity.ok("Password reset email sent.");
    }
}
