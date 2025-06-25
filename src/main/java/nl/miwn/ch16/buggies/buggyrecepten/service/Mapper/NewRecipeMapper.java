package nl.miwn.ch16.buggies.buggyrecepten.service.Mapper;

import nl.miwn.ch16.buggies.buggyrecepten.dto.NewRecipeDTO;
import nl.miwn.ch16.buggies.buggyrecepten.model.Ingredient;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */
public class NewRecipeMapper {

    public static Recipe fromDTO(NewRecipeDTO newRecipeDTO){
        Recipe recipe = new Recipe();

        recipe.setName(newRecipeDTO.getRecipeName());
        recipe.setRecipeSteps(newRecipeDTO.getRecipeSteps());



        return recipe;
    }
}
