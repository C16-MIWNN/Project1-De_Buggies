package nl.miwn.ch16.buggies.buggyrecepten.service;

import nl.miwn.ch16.buggies.buggyrecepten.dto.NewRecipeDTO;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
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

    public NewRecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void saveRecipe(Recipe newRecipe) {
        recipeRepository.save(newRecipe);
    }

    public void toggleFavorite(Long recipeId) {
        Optional<Recipe> recipeToBeFavorited = recipeRepository.findById(recipeId);

        if (recipeToBeFavorited.isPresent()) {
            Recipe recipe = recipeToBeFavorited.get();
            recipe.setFavorite(!recipe.isFavorite());
            recipeRepository.save(recipe);
        }
    }
}
