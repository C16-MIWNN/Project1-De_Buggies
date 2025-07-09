package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.model.User;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.UserRepository;
import nl.miwn.ch16.buggies.buggyrecepten.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final UserRepository userRepository;

    public UserController(RecipeRepository recipeRepository, CustomUserDetailsService customUserDetailsService,
                          UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String userProfile(Model model) {
        Optional<User> user = customUserDetailsService.getCurrentUser();

        if (user.isEmpty()) {
            return "redirect:/login";
        }

        List<Recipe> favoriteRecipes = recipeRepository.findAllByFavoritedByUsersContaining(user.get());

        model.addAttribute("user", user.get());
        model.addAttribute("favoriteRecipe", favoriteRecipes);

        return "personalPage";
    }

    @GetMapping("/overview")
    private String showAllUsersPage(Model datamodel) {
        datamodel.addAttribute("allUsers", userRepository.findAll());

        return "allUsersPage";
    }

    @GetMapping("/delete/{userId}")
    private String deleteCourse(@PathVariable("userId") Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(userRepository::delete);

        return "redirect:/user/overview";
    }
}