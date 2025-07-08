package nl.miwn.ch16.buggies.buggyrecepten.service;

import nl.miwn.ch16.buggies.buggyrecepten.model.AdminUser;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.model.User;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */

@Service
public class NewRecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public NewRecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public void saveRecipe(Recipe newRecipe) {
        recipeRepository.save(newRecipe);
    }

    public void toggleFavorite(Long recipeId, String name) {
        Optional<Recipe> recipeToBeFavorited = recipeRepository.findById(recipeId);
        Optional<User> userOpt = userRepository.findByName(name);

        if (recipeToBeFavorited.isPresent() && userOpt.isPresent()) {
            Recipe recipe = recipeToBeFavorited.get();
            User user = userOpt.get();

            if (user.getFavoriteRecipes().contains(recipe)) {
                user.getFavoriteRecipes().remove(recipe);
            } else {
                user.getFavoriteRecipes().add(recipe);
            }

           userRepository.save(user);
        }
    }
}
