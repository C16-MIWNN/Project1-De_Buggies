package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.AdminUser;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.AdminUserRepository;
import nl.miwn.ch16.buggies.buggyrecepten.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author Marnix Ripke
 * Handles requests related to users of the application.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private final AdminUserRepository adminUserRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public UserController(AdminUserRepository adminUserRepository, CustomUserDetailsService customUserDetailsService) {
        this.adminUserRepository = adminUserRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/profile")
    public String userProfile(Model model) {
        Optional<AdminUser> user = customUserDetailsService.getCurrentUser();
        model.addAttribute("user", user);
        return "personalPage";
    }

}
