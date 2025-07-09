package com.empress.usermanagementapi.controller;

import com.empress.usermanagementapi.entity.PasswordResetToken;
import com.empress.usermanagementapi.service.EmailService;
import com.empress.usermanagementapi.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForgotPasswordController {

    private final PasswordResetService resetService;
    private final EmailService emailService;

    public ForgotPasswordController(PasswordResetService resetService,
                                    EmailService emailService) {
        this.resetService = resetService;
        this.emailService = emailService;
    }

    @GetMapping("/forgot-password")
    public String displayForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            PasswordResetToken token = resetService.createPasswordResetTokenForEmail(email);
            emailService.sendPasswordResetEmail(email, token.getToken());
            model.addAttribute("message", "A reset link has been sent to your email.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String displayResetPasswordPage(@RequestParam("token") String token, Model model) {
        String err = resetService.validatePasswordResetToken(token);
        if (err != null) {
            model.addAttribute("error", err);
            return "forgot-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model) {
        String err = resetService.resetPassword(token, password);
        if (err != null) {
            model.addAttribute("error", err);
            model.addAttribute("token", token);
            return "reset-password";
        }
        return "redirect:/login?resetSuccess";
    }
}
