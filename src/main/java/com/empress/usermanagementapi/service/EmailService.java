package com.empress.usermanagementapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String fromAddress;

    // Replace with your deployed host or derive dynamically
    @Value("${app.base-url:https://your-app.up.railway.app}")
    private String appBaseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String to, String token) {
        String resetLink = appBaseUrl + "/reset-password?token=" + token;
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAddress);
        msg.setTo(to);
        msg.setSubject("Your Password Reset Link");
        msg.setText("Hi,\n\n"
            + "You requested a password reset. Click the link below to set a new password:\n"
            + resetLink + "\n\n"
            + "If you didn’t request this, just ignore this email.\n\n"
            + "Thanks,\n"
            + "User Management Team");
        mailSender.send(msg);
    }
}
