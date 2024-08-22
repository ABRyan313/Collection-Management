package in.sp.itransition.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Thymeleaf template for login page
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Thymeleaf template for registration page
    }
}
