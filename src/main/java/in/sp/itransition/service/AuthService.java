package in.sp.itransition.service;

import in.sp.itransition.model.User;
import in.sp.itransition.repository.UserRepository;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
   
    private final Map<String, Integer> loginAttempts = new HashMap<>();
    private static final int MAX_ATTEMPTS = 5;

    public AuthService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder, 
                       UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        
    }

    /**
     * Registers a new user after validating that the email is not already in use.
     *
     * @param name     User's name
     * @param email    User's email
     * @param password User's password
     * @throws IllegalArgumentException if email is already in use
     */
    public void register(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRegistrationTime(LocalDateTime.now());
        user.setStatus(User.Status.ACTIVE);

        userRepository.save(user);
    }

    /**
     * Authenticates a user by email and password while tracking login attempts.
     *
     * @param email    User's email
     * @param password User's password
     * @throws RuntimeException if authentication fails or account is locked
     */
    public void authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No account found with that email."));

        if (loginAttempts.getOrDefault(email, 0) >= MAX_ATTEMPTS) {
            throw new RuntimeException("Account locked due to too many failed login attempts.");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            loginAttempts.remove(email);
            // Token generation logic can be implemented here if needed
        } else {
            loginAttempts.put(email, loginAttempts.getOrDefault(email, 0) + 1);
            throw new BadCredentialsException("Invalid email or password.");
        }
    }

    /**
     * Resets login attempts for a specific email. Can be used after successful password reset or admin intervention.
     *
     * @param email User's email
     */
    public void resetLoginAttempts(String email) {
        loginAttempts.remove(email);
    }
}
