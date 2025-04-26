package com.empress.usermanagementapi.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello, Empress!";
    	}

	@PostMapping("/users")
	public String createUser() {
    		return "User created!";
	}

	@GetMapping("/users")
	public String getAllUsers() {
	    return "List of users!";
	}

	@PutMapping("/users/{id}")
	public String updateUser(@PathVariable Long id) {
    		return "User with ID " + id + " updated!";
	}

	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable Long id) {
    		return "User with ID " + id + " deleted!";
	}

}

