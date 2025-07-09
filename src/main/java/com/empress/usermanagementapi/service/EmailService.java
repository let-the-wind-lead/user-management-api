package com.empress.usermanagementapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Password Reset Request");
        msg.setText(
            "You requested to reset your password.\n\n" +
            "Click the link below to set a new password:\n" +
            resetLink + "\n\n" +
            "If you didn't request this, you can ignore this email."
        );
        mailSender.send(msg);
    }
}
