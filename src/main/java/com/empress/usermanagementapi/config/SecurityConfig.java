package com.empress.usermanagementapi.config;

import com.empress.usermanagementapi.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(authz -> authz
        .requestMatchers("/hello").permitAll()
        .requestMatchers("/users/**").hasRole("ADMIN")
        .anyRequest().authenticated()
      )
      .httpBasic(Customizer.withDefaults())
      .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepo) {
    return username -> userRepo.findByUsername(username)
      .map(u -> User.withUsername(u.getUsername())
                    .password(u.getPassword())       // from DB, already BCrypt-encoded
                    .roles(u.getRole().name())       // “ADMIN” or “USER”
                    .build())
      .orElseThrow(() -> new UsernameNotFoundException("No such user: " + username));
  }
}
