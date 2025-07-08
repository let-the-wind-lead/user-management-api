package com.empress.usermanagementapi.config;

import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(authz -> authz
            .requestMatchers("/hello").permitAll()
            .requestMatchers("/users/**").hasRole("ADMIN")
            .anyRequest().authenticated()
          )
          .httpBasic(Customizer.withDefaults());   // ← restores the browser’s popup
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User u = userRepo.findByUsername(username);
            if (u == null) {
                throw new UsernameNotFoundException("No such user: " + username);
            }
            return org.springframework.security.core.userdetails.User.builder()
                .username(u.getUsername())
                .password(u.getPassword())   // expects BCrypt hash from DB
                .roles(u.getRole().name())   // “ADMIN” or “USER”
                .build();
        };
    }
}
