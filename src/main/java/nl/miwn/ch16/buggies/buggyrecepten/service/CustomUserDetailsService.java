package nl.miwn.ch16.buggies.buggyrecepten.service;

import nl.miwn.ch16.buggies.buggyrecepten.model.AdminUser;
import nl.miwn.ch16.buggies.buggyrecepten.model.NormalUser;
import nl.miwn.ch16.buggies.buggyrecepten.model.User;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role;
        if (user instanceof AdminUser) {
            role = "ROLE_ADMIN";
        } else if (user instanceof NormalUser) {
            role = "ROLE_NORMAL";
        } else {
            throw new UsernameNotFoundException("Unknown user type");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }

    public Optional<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userRepository.findByName(name);
    }
}
