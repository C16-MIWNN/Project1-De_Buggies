package nl.miwn.ch16.buggies.buggyrecepten.service;

import nl.miwn.ch16.buggies.buggyrecepten.dto.NewRecipeDTO;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientPerRecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.service.Mapper.NewRecipeMapper;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */
public class NewRecipeService {

    private final RecipeRepository recipeRepository;

    public NewRecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void saveRecipe(Recipe newRecipe) {

    }

    public void save(NewRecipeDTO newRecipeDTO) {
        saveRecipe(NewRecipeMapper.fromDTO(newRecipeDTO));;
    }
}
