package com.empress.usermanagementapi.config;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";   // maps to src/main/resources/templates/login.html
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("role", "ADMIN");
        return "admin";   // maps to admin.html
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("role", "USER");
        return "user";    // maps to user.html
    }
}
