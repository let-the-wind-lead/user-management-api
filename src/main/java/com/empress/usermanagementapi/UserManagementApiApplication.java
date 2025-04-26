package com.empress.usermanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class UserManagementApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserManagementApiApplication.class, args);
	}

}
