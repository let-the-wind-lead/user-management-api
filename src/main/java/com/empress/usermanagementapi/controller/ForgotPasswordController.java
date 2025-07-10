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
    public String showForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForm(@RequestParam String username,
                             @RequestParam String email,
                             Model model) {
        Optional<User> opt = userRepo.findByUsernameAndEmail(username, email);
        // Always show the same message for security
        model.addAttribute("message",
            "If an account matches those details, you’ll receive an email shortly."
        );
        if (opt.isPresent()) {
            String token = UUID.randomUUID().toString();
            // TODO: persist the token associated to user with expiry
            String resetLink = "https://your-domain.com/reset-password?token=" + token;
    
            // *** DEBUG: put the link into the model so the template can show it ***
            model.addAttribute("debugResetLink", resetLink);
    
            emailService.sendPasswordResetEmail(email, resetLink);
        }
        return "forgot-password";
    }

}
