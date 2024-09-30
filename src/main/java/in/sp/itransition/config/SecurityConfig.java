package in.sp.itransition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                .requestMatchers("/register", "/login", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/home").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/collections/**").authenticated()
                .requestMatchers("/collections/*/items/**").authenticated() // Authenticated access for collections
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Custom login page
                .usernameParameter("email") // Use email as username
                .defaultSuccessUrl("/home", true) // Redirect to /home on successful login
                .permitAll()
            )
            .logout(logout -> logout
                    // Custom logout handling
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/login?invalid-session=true")  // Redirect on invalid session
                )
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/h2-console/**") // Example for ignoring CSRF on specific paths
                );// Disable CSRF for simplicity; enable for production use!

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    User userBean() {
        return new User();
    }
    
    
}
