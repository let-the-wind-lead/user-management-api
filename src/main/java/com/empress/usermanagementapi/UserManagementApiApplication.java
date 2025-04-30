package com.empress.usermanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.empress.usermanagementapi.repository")
@SpringBootApplication
public class UserManagementApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementApiApplication.class, args);
    }
}
