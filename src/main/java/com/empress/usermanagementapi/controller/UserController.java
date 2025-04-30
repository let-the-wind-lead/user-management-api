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


@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/hello")
	public String hello() {
		return "Hello, Empress!";
    	}

	@PostMapping("/users")
	public User createUser() {
	    User user = new User();
	    user.setUsername("newuser");
	    user.setPassword("pass"); // ideally encoded
	    user.setEmail("newuser@example.com");
	    user.setRole(Role.USER);
	    return userRepository.save(user);
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
	    return userRepository.findAll();
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

