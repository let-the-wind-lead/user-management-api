package com.empress.usermanagementapi.config;

import org.springframework.security.config.Customizer;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


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
	        .httpBasic(Customizer.withDefaults()) // 🌟 Correct syntax here
	        .build(); // 🌟 Notice build() is connected here
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
	public UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
			.username("test")
			.password(passwordEncoder().encode("test"))
			.roles("USER")
			.build();

	 	UserDetails admin = User.builder()
			.username("admin")
			.password(passwordEncoder().encode("admin"))
			.roles("ADMIN")
			.build();

	    return new InMemoryUserDetailsManager(user, admin);
	}


}

