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

    private final UserRepository userRepo;
    private final EmailService emailService;

    @Autowired
    public ForgotPasswordController(UserRepository userRepo,
                                    EmailService emailService) {
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @GetMapping("/forgot-password")
    public String showForm(Model model) {
        // Clear debug on fresh GET
        model.addAttribute("debugReceived", null);
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForm(@RequestParam String username,
                             @RequestParam String email,
                             Model model) {
        // 0) Always echo back what we received
        String received = "username=" + username + " email=" + email;
        model.addAttribute("debugReceived", received);

        Optional<User> opt = userRepo.findByUsernameAndEmail(username, email);

        // 1) generic info banner
        model.addAttribute("message",
            "If an account matches those details, you’ll receive an email shortly."
        );

        if (opt.isPresent()) {
            // 2) build token + link
            String token = UUID.randomUUID().toString();
            String resetLink = "https://your-domain.com/reset-password?token=" + token;
            model.addAttribute("debugResetLink", resetLink);

            // 3) send the email
            emailService.sendPasswordResetEmail(email, resetLink);
        }

        return "forgot-password";
    }
}
