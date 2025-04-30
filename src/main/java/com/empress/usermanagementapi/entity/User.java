package com.empress.usermanagementapi.entity;
import com.empress.usermanagementapi.entity.Role;


import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;

	//role stuff
	public Role getRole() {
	    return role;
	}
	public void setRole(Role role) {
	    this.role = role;
	}

    	// Getters and setters will be added later
	public Long getId() {
    		return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}

	public String getUsername() {
	    return username;
	}

	public void setUsername(String username) {
	    this.username = username;
	}

	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public String getPassword() {
	    return password;
	}

	public void setPassword(String password) {
	    this.password = password;
	}

}
