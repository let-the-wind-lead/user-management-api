package com.empress.usermanagementapi.config;

import com.empress.usermanagementapi.entity.User;
import com.empress.usermanagementapi.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {

    private final UserRepository userRepo;

    public PageController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        User u = userRepo.findByUsername(principal.getName());
        model.addAttribute("username", u.getUsername());
        model.addAttribute("role", "ADMIN");
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User u = userRepo.findByUsername(principal.getName());
        model.addAttribute("username", u.getUsername());
        model.addAttribute("role", u.getRole().name());
        model.addAttribute("email", u.getEmail());
        return "user";
    }
}
