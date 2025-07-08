package com.empress.usermanagementapi.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  @GetMapping("/login")
  public String login() {
    return "login";   // src/main/resources/templates/login.html
  }

  @GetMapping("/admin")
  public String admin() {
    return "admin";   // src/main/resources/templates/admin.html
  }

  @GetMapping("/user")
  public String user() {
    return "user";    // src/main/resources/templates/user.html
  }
}
