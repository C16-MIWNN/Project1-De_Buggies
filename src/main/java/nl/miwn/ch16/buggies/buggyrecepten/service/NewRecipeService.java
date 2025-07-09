package nl.miwn.ch16.buggies.buggyrecepten.service;

import nl.miwn.ch16.buggies.buggyrecepten.dto.NewRecipeDTO;
import nl.miwn.ch16.buggies.buggyrecepten.model.IngredientPerRecipe;
import nl.miwn.ch16.buggies.buggyrecepten.model.NormalUser;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.model.User;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientPerRecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.UserRepository;
import nl.miwn.ch16.buggies.buggyrecepten.service.Mapper.NewRecipeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * @Author: Joost Numan
 * this is a service that connects the recipe controller to the new recipe DTO mapper
 */

@Service
public class NewRecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final IngredientPerRecipeRepository ingredientPerRecipeRepository;
    private final NewRecipeMapper newRecipeMapper;

    public NewRecipeService(RecipeRepository recipeRepository,
                            UserRepository userRepository,
                            IngredientPerRecipeRepository ingredientPerRecipeRepository,
                            NewRecipeMapper newRecipeMapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.ingredientPerRecipeRepository = ingredientPerRecipeRepository;
        this.newRecipeMapper = newRecipeMapper;
    }

    public void saveRecipe(NewRecipeDTO newRecipeDTO, NormalUser creator) {
        Recipe recipe = newRecipeMapper.RecipeFromDTO(newRecipeDTO, creator);
        List<IngredientPerRecipe> ingredients = newRecipeMapper.IngredientPerRecipeListFromDTO(newRecipeDTO, recipe);

        recipe.setIngredientPerRecipeList(ingredients);

        recipeRepository.save(recipe);
        ingredientPerRecipeRepository.saveAll(ingredients);
    }

    public void toggleFavorite(Long recipeId, String name) {
        Optional<Recipe> recipeToBeFavorited = recipeRepository.findById(recipeId);
        Optional<User> userOpt = userRepository.findByName(name);

        if (recipeToBeFavorited.isPresent() && userOpt.isPresent()) {
            Recipe recipe = recipeToBeFavorited.get();
            NormalUser user = (NormalUser) userOpt.get();

            if (user.getFavoriteRecipes().contains(recipe)) {
                user.getFavoriteRecipes().remove(recipe);
            } else {
                user.getFavoriteRecipes().add(recipe);
            }

           userRepository.save(user);
        }
    }
}
