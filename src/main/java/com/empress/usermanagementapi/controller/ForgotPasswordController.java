package com.empress.usermanagementapi.controller;

import com.empress.usermanagementapi.entity.PasswordResetToken;
import com.empress.usermanagementapi.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForgotPasswordController {

    private final PasswordResetService resetService;

    public ForgotPasswordController(PasswordResetService resetService) {
        this.resetService = resetService;
    }

    @GetMapping("/forgot-password")
    public String displayForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            PasswordResetToken token = resetService.createPasswordResetTokenForEmail(email);
            // In real life, send email. For demo, show token link:
            model.addAttribute("message",
                "Reset link (demo) → " +
                "<a href=\"/reset-password?token=" + token.getToken() + "\">Reset Password</a>");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String displayResetPasswordPage(@RequestParam("token") String token, Model model) {
        String error = resetService.validatePasswordResetToken(token);
        if (error != null) {
            model.addAttribute("message", error);
            return "forgot-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model) {
        String error = resetService.resetPassword(token, password);
        if (error != null) {
            model.addAttribute("message", error);
            model.addAttribute("token", token);
            return "reset-password";
        }
        return "redirect:/login?resetSuccess";
    }
}
