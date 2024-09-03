package in.sp.itransition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import in.sp.itransition.model.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/register", "/login", "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/home").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/collections/**").authenticated() // Allow authenticated users to access collection-related endpoints
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Custom login page
                .usernameParameter("email") // Use email as username
                .defaultSuccessUrl("/home", true) // Redirect to /home on successful login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL for logout
                .logoutSuccessUrl("/login?logout") // Redirect to login page with logout message
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**") // Example for ignoring CSRF on specific paths
            );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    User userBean() {
        // Create and configure your User bean here if needed
        return new User();
    }
}
