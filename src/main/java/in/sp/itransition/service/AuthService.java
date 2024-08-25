package in.sp.itransition.service;

import in.sp.itransition.model.User;
import in.sp.itransition.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    

    // Map to track login attempts: key -> email, value -> number of attempts
    private final Map<String, Integer> loginAttempts = new HashMap<>();
    private static final int MAX_ATTEMPTS = 5;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
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
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRegistrationTime(LocalDateTime.now());
        user.setStatus(User.Status.ACTIVE); // Ensure you have a Status enum in User model

        userRepository.save(user);
    }

    /**
     * Authenticates a user by email and password while tracking login attempts.
     *
     * @param email    User's email
     * @param password User's password
     * @throws RuntimeException if authentication fails or account is locked
     */
    public void login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if account is locked due to too many failed attempts
        if (loginAttempts.getOrDefault(email, 0) >= MAX_ATTEMPTS) {
            throw new RuntimeException("Account locked due to too many failed login attempts");
        }

        // Verify password
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Successful login, reset login attempts
            loginAttempts.remove(email);
            // Generate authentication token if needed
            String token = generateToken(user);
            // You can store or return the token as per your application's requirement
        } else {
            // Failed login, increment login attempts
            loginAttempts.put(email, loginAttempts.getOrDefault(email, 0) + 1);
            throw new RuntimeException("Invalid email or password");
        }
    }

    /**
     * Generates an authentication token for the user.
     *
     * @param user Authenticated user
     * @return Generated token as a String
     */
    private String generateToken(User user) {
        // Implement your token generation logic here
        // For example, using JWT:
        // return jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        return "dummyToken"; // Placeholder implementation
    }

    /**
     * Resets login attempts for a specific email. Can be used after successful password reset or admin intervention.
     *
     * @param email User's email
     */
    public void resetLoginAttempts(String email) {
        loginAttempts.remove(email);
    }
    public void authenticate(String email, String password) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No account found with that email.");
        }
    
}
}
