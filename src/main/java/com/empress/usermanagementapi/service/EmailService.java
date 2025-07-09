package com.empress.usermanagementapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailService(JavaMailSender mailSender,
                        @Value("${spring.mail.from}") String fromAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    /**
     * Sends a password-reset link to the given email.
     * No-ops if fromAddress is blank or mailSender isn’t configured.
     */
    public void sendPasswordReset(String recipientEmail, String resetToken) {
        if (fromAddress == null || fromAddress.isBlank()) {
            // stub out if not configured
            return;
        }
        String resetLink = "https://your-app-domain/reset?token=" + resetToken;

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAddress);
        msg.setTo(recipientEmail);
        msg.setSubject("Your Password Reset Request");
        msg.setText("Click here to reset your password:\n\n" + resetLink);
        mailSender.send(msg);
    }
}
