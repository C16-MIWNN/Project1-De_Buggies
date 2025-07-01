package nl.miwn.ch16.buggies.buggyrecepten.service;

import nl.miwn.ch16.buggies.buggyrecepten.model.AdminUser;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.AdminUserRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */

@Service
public class NewRecipeService {
    private final RecipeRepository recipeRepository;
    private final AdminUserRepository adminUserRepository;

    public NewRecipeService(RecipeRepository recipeRepository, AdminUserRepository adminUserRepository) {
        this.recipeRepository = recipeRepository;
        this.adminUserRepository = adminUserRepository;
    }

    public void saveRecipe(Recipe newRecipe) {
        recipeRepository.save(newRecipe);
    }

    public void toggleFavorite(Long recipeId, String name) {
        Optional<Recipe> recipeToBeFavorited = recipeRepository.findById(recipeId);
        Optional<AdminUser> userOpt = adminUserRepository.findByName(name);

        if (recipeToBeFavorited.isPresent() && userOpt.isPresent()) {
            Recipe recipe = recipeToBeFavorited.get();
            AdminUser user = userOpt.get();

            if (user.getFavoriteRecipes().contains(recipe)) {
                user.getFavoriteRecipes().remove(recipe);
            } else {
                user.getFavoriteRecipes().add(recipe);
            }

            adminUserRepository.save(user);
        }
    }
}
