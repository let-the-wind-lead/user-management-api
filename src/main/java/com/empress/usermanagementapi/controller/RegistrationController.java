package com.empress.usermanagementapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.entity.Role;
import com.empress.usermanagementapi.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerSubmit(
        @ModelAttribute("userForm") User userForm,
        Model model
    ) {
        // check username first
        if (userService.usernameExists(userForm.getUsername())) {
            model.addAttribute("usernameError", "Username already in use");
            return "register";
        }
        // then check email
        if (userService.emailExists(userForm.getEmail())) {
            model.addAttribute("emailError", "Email already in use");
            return "register";
        }
        userForm.setRole(Role.USER);
        userService.create(userForm);
        return "redirect:/login?registered";
    }
}
