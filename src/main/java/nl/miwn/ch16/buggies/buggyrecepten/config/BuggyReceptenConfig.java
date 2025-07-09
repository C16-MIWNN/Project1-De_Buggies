package nl.miwn.ch16.buggies.buggyrecepten.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "/homePage","recipe/detail/{name}").permitAll()
                        .requestMatchers("/webjars/**", "/css/**", "/images/**").permitAll()
                        .requestMatchers("/user/overview").hasRole("ADMIN")
                        .requestMatchers("/user/delete/{userId}").hasRole("ADMIN")
                        .requestMatchers("/category/new").hasRole("ADMIN")
                        .requestMatchers("recipe/new", "/recipe/edit/").hasRole("NORMAL")
                        .anyRequest().hasRole("NORMAL")
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}