package com.empress.usermanagementapi.controller;

import com.empress.usermanagementapi.entity.Role;
import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("userForm") User userForm) {
        userForm.setRole(Role.USER);
        userService.create(userForm);
        return "redirect:/login?registered";
    }
}
