package com.empress.usermanagementapi.service;

import com.empress.usermanagementapi.entity.PasswordResetToken;
import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.PasswordResetTokenRepository;
import com.empress.usermanagementapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(PasswordResetTokenRepository tokenRepo,
                                UserRepository userRepo,
                                PasswordEncoder passwordEncoder) {
        this.tokenRepo = tokenRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordResetToken createPasswordResetTokenForEmail(String email) {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("No user with email: " + email));
        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken(
            token,
            user,
            LocalDateTime.now().plusHours(24)
        );
        return tokenRepo.save(prt);
    }

    public String validatePasswordResetToken(String token) {
        return tokenRepo.findByToken(token)
            .filter(prt -> prt.getExpiryDate().isAfter(LocalDateTime.now()))
            .map(prt -> (String) null)
            .orElse("Invalid or expired token");
    }

    public String resetPassword(String token, String newPassword) {
        return tokenRepo.findByToken(token)
            .filter(prt -> prt.getExpiryDate().isAfter(LocalDateTime.now()))
            .map(prt -> {
                User u = prt.getUser();
                u.setPassword(passwordEncoder.encode(newPassword));
                userRepo.save(u);
                tokenRepo.delete(prt);
                return (String) null;
            })
            .orElse("Invalid or expired token");
    }
}
