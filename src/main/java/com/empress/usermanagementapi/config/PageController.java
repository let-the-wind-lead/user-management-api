package com.empress.usermanagementapi.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";   // resolves to login.html
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";   // resolves to admin.html
    }

    @GetMapping("/user")
    public String user() {
        return "user";    // resolves to user.html
    }
}
