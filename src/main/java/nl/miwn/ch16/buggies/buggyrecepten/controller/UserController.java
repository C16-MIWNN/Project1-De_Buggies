package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.AdminUser;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

/**
 * @author Marnix Ripke
 * Handles requests related to users of the application.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private final RecipeRepository recipeRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public UserController(RecipeRepository recipeRepository, CustomUserDetailsService customUserDetailsService) {
        this.recipeRepository = recipeRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/profile")
    public String userProfile(Model model) {
        Optional<AdminUser> user = customUserDetailsService.getCurrentUser();

        if (user.isEmpty()) {
            return "redirect:/login";
        }

        List<Recipe> favoriteRecipes = recipeRepository.findAllByFavoritedByAdminsContaining(user.get());

        model.addAttribute("user", user.get());
        model.addAttribute("favoriteRecipe", favoriteRecipes);

        return "personalPage";
    }
}