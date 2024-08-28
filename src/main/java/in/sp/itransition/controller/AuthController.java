package in.sp.itransition.controller;

import in.sp.itransition.service.AuthService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final AuthService authService;

    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Thymeleaf template for login page
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        Model model) {
        try {
            authService.authenticate(email, password);
            return "redirect:/home"; // Redirect after successful login
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "No account found with that email.");
            return "login"; // Return to login page with error message
        } catch (BadCredentialsException ex) {
            model.addAttribute("error", "Invalid email or password.");
            return "login"; // Return to login page with error message
        } catch (Exception ex) {
            model.addAttribute("error", "An unexpected error occurred.");
            return "login"; // Return to login page with error message
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Thymeleaf template for registration page
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, 
                           @RequestParam String email, 
                           @RequestParam String password, 
                           Model model) {
        try {
            authService.register(name, email, password);
            return "redirect:/login"; // Redirect to login page after successful registration
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register"; // Return to registration page with error message
        }
    }
}
