package in.sp.itransition.controller;

import in.sp.itransition.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Thymeleaf template for login page
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        Model model) {
        try {
            authService.authenticate(username, password);
            return "redirect:/home"; // or wherever you want to redirect after successful login
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "No account found with that email.");
            return "login"; // return to login page with error message
        } catch (BadCredentialsException ex) {
            model.addAttribute("error", "Invalid email or password.");
            return "login"; // return to login page with error message
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Thymeleaf template for registration page
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            authService.register(name, email, password);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
