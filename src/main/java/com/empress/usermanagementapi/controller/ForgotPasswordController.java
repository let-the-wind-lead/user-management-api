package com.empress.usermanagementapi.controller;

import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.UserRepository;
import com.empress.usermanagementapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

    @GetMapping("/forgot-password")
    public String showResetForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleReset(@RequestParam("email") String email, Model model) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            // for demo: generate a random token
            String token = UUID.randomUUID().toString();
            // TODO: persist token & user association, expire time, etc.
            String resetLink = "https://your-domain.com/reset-password?token=" + token;
            emailService.sendPasswordResetEmail(email, resetLink);
            model.addAttribute("message", "If that address exists you’ll receive a reset link.");
        } else {
            model.addAttribute("message", "If that address exists you’ll receive a reset link.");
        }
        return "forgot-password";
    }
}
