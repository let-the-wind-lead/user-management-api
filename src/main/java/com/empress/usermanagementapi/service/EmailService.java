package com.empress.usermanagementapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String from;

    @Autowired
    public EmailService(JavaMailSender mailSender,
                        @Value("${spring.mail.from}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    /**
     * Sends a one-time password reset link to the user.
     *
     * @param toEmail the recipient’s address
     * @param token   the generated reset token
     */
    public void sendPasswordResetEmail(String toEmail, String token) {
        String resetUrl = "https://user-management-api-production-7709.up.railway.app/reset-password?token=" + token;

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(toEmail);
        msg.setSubject("Your Password Reset Request");
        msg.setText(
            "Hello,\n\n" +
            "We received a request to reset your password. Click the link below to pick a new one:\n\n" +
            resetUrl + "\n\n" +
            "If you didn’t request this, just ignore this email.\n\n" +
            "Thanks,\n" +
            "Your App Team"
        );

        mailSender.send(msg);
    }
}
