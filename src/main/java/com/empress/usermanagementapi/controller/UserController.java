package com.empress.usermanagementapi.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empress.usermanagementapi.repository.UserRepository;
import com.empress.usermanagementapi.entity.User;

import com.empress.usermanagementapi.entity.Role;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.security.access.prepost.PreAuthorize;


@RestController
public class UserController {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/hello")
	public String hello() {
		return "Hello, Empress!";
    	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    return userRepository.save(user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/users")
	public List<User> getAllUsers() {
	    return userRepository.findAll();
	}

	@PutMapping("/users/{id}")
	public String updateUser(@PathVariable Long id) {
    		return "User with ID " + id + " updated!";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable Long id) {
    		return "User with ID " + id + " deleted!";
	}

}

