package nl.miwn.ch16.buggies.buggyrecepten.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
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
                        .requestMatchers("/", "/homePage").permitAll()
                        .requestMatchers("/webjars/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .logout((logout) -> logout.logoutSuccessUrl("/"));

        return httpSecurity.build();
    }

}