package nl.miwn.ch16.buggies.buggyrecepten.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

/**
 * @author Marnix Ripke
 * Configures the security for the application
 */

@Configuration
@EnableWebSecurity
public class BuggyReceptenConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/homePage", "recipe/detail/{name}").permitAll()
                        .requestMatchers("/webjars/**", "/css/**", "/images/**").permitAll()
                        .requestMatchers("/user/overview", "/user/delete/{userId}",
                                         "/category/new").hasRole("ADMIN")
                        .requestMatchers("recipe/new", "/recipe/edit/").hasRole("NORMAL")
                        .anyRequest().hasRole("NORMAL")
                )
                .formLogin(form -> form
                        .successHandler(this::onAuthenticationSuccess)
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/"));

        return httpSecurity.build();
    }

    void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            response.sendRedirect("/user/overview");
        } else {
            response.sendRedirect("/");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}