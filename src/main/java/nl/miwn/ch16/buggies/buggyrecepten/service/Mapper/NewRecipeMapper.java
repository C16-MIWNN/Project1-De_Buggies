package nl.miwn.ch16.buggies.buggyrecepten.service.Mapper;

import nl.miwn.ch16.buggies.buggyrecepten.dto.NewRecipeDTO;
import nl.miwn.ch16.buggies.buggyrecepten.model.*;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * @Author: Joost Numan
 * this is a mapper that creates a recipe, ingredients if not already made and th ingredients per recipe from a DTO
 */

@Component
public class NewRecipeMapper {
    private final IngredientRepository ingredientRepository;

    public NewRecipeMapper(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Recipe RecipeFromDTO(NewRecipeDTO newRecipeDTO, NormalUser creator) {
        Recipe recipe = new Recipe();
        recipe.setName(newRecipeDTO.getRecipeName());
        recipe.setRecipeStepsList(newRecipeDTO.getRecipeStepsList());
        recipe.setCategories(newRecipeDTO.getCategories());
        recipe.setCreator(creator);
        return recipe;
    }

    public List<IngredientPerRecipe> IngredientPerRecipeListFromDTO(NewRecipeDTO newRecipeDTO, Recipe recipe) {
        List<IngredientPerRecipe> ingredientPerRecipeList = new ArrayList<>();

        for (int index = 0; index < newRecipeDTO.getIngredientsList().size(); index++) {
            String name = newRecipeDTO.getIngredientsList().get(index);
            String unit = newRecipeDTO.getUnits().get(index);
            String amount = newRecipeDTO.getAmounts().get(index);

            Ingredient ingredient = findOrCreateIngredient(name);

            IngredientPerRecipe ingredientPerRecipe = new IngredientPerRecipe();
            ingredientPerRecipe.setRecipe(recipe);
            ingredientPerRecipe.setIngredient(ingredient);
            ingredientPerRecipe.setUnit(unit);
            ingredientPerRecipe.setAmount(amount);

            ingredientPerRecipeList.add(ingredientPerRecipe);
        }

        return ingredientPerRecipeList;
    }

    private Ingredient findOrCreateIngredient(String name) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findByName(name);
        if (optionalIngredient.isPresent()) {
            return optionalIngredient.get();
        } else {
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(name);
            return ingredientRepository.save(newIngredient);
        }
    }
}