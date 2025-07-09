package com.empress.usermanagementapi.config;

import com.empress.usermanagementapi.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model m) {
        m.addAttribute("userForm", new User());
        return "register";
    }

    @GetMapping("/admin")
    public String admin(Model m, Principal p) {
        m.addAttribute("username", p.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model m, Principal p) {
        m.addAttribute("username", p.getName());
        return "user";
    }
}
