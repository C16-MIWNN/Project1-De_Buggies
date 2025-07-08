package nl.miwn.ch16.buggies.buggyrecepten.service;

import nl.miwn.ch16.buggies.buggyrecepten.dto.NewRecipeDTO;
import nl.miwn.ch16.buggies.buggyrecepten.model.AdminUser;
import nl.miwn.ch16.buggies.buggyrecepten.model.Ingredient;
import nl.miwn.ch16.buggies.buggyrecepten.model.IngredientPerRecipe;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.AdminUserRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientPerRecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */

@Service
public class NewRecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final AdminUserRepository adminUserRepository;
    private final IngredientPerRecipeRepository ingredientPerRecipeRepository;

    public NewRecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, AdminUserRepository adminUserRepository, IngredientPerRecipeRepository ingredientPerRecipeRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.adminUserRepository = adminUserRepository;
        this.ingredientPerRecipeRepository = ingredientPerRecipeRepository;
    }

    public void saveRecipe(NewRecipeDTO newRecipeDTO, AdminUser creator) {
        Recipe newRecipe = new Recipe();

        newRecipe.setRecipeStepsList(newRecipeDTO.getRecipeStepsList());
        newRecipe.setName(newRecipeDTO.getRecipeName());
        newRecipe.setCategories(newRecipeDTO.getCategories());
        newRecipe.setCreator(creator);

        recipeRepository.save(newRecipe);

        List<IngredientPerRecipe> ingredientPerRecipeList = new ArrayList<>();

        for (int index = 0; index < newRecipeDTO.getIngredientsList().size(); index++) {
            String ingredientName = newRecipeDTO.getIngredientsList().get(index);
            Ingredient ingredientEntity;

            Optional<Ingredient> optionalIngredient = ingredientRepository.findByName(ingredientName);
            if (optionalIngredient.isPresent()) {
                ingredientEntity = optionalIngredient.get();
            } else {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setName(ingredientName);
                ingredientEntity = ingredientRepository.save(newIngredient);
            }

            IngredientPerRecipe newIngredientPerRecipe = new IngredientPerRecipe();
            newIngredientPerRecipe.setRecipe(newRecipe);
            newIngredientPerRecipe.setUnit(newRecipeDTO.getUnits().get(index));
            newIngredientPerRecipe.setAmount(newRecipeDTO.getAmounts().get(index));
            newIngredientPerRecipe.setIngredient(ingredientEntity);

            ingredientPerRecipeRepository.save(newIngredientPerRecipe);
            ingredientPerRecipeList.add(newIngredientPerRecipe);

        }

        newRecipe.setIngredientPerRecipeList(ingredientPerRecipeList);

        System.out.println(recipeRepository.findByName(newRecipe.getName()).get().getIngredientPerRecipeList());
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
