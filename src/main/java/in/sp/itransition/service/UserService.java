package in.sp.itransition.service;

import in.sp.itransition.model.User;
import in.sp.itransition.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        // Check if email is already registered
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationTime(LocalDateTime.now()); // Set registration time
        user.setStatus(User.Status.ACTIVE); // Set default status

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
}
}