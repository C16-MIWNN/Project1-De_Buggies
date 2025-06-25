package nl.miwn.ch16.buggies.buggyrecepten.service.Mapper;

import nl.miwn.ch16.buggies.buggyrecepten.dto.NewRecipeDTO;
import nl.miwn.ch16.buggies.buggyrecepten.model.IngredientPerRecipe;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */
public class NewIngredientPerRecipeMapper {

    public static List<IngredientPerRecipe> ingredientFromDTO(NewRecipeDTO newRecipeDTO){
        List<IngredientPerRecipe> ingredientsPerRecipe = new ArrayList<>();

        String[] ingredientQuantities = splitIngredientQuantities(newRecipeDTO.getIngredientNames());
        String[] ingredientUnits = splitIngredientUnits(newRecipeDTO.getIngredientUnitsOfMeasurement());

        for (int i = 0; i < ingredientQuantities.length; i++) {
            IngredientPerRecipe newIngredientPerRecipe = new IngredientPerRecipe();

            newIngredientPerRecipe.setQuantity(Double.parseDouble(ingredientQuantities[i].trim()));
            newIngredientPerRecipe.setUnitOfMeasurement(ingredientUnits[i]);

            ingredientsPerRecipe.add(newIngredientPerRecipe);
        }

        return ingredientsPerRecipe;
    }

    public static String[] splitIngredientNames(String recipeQuantities) {
        String[] names = recipeQuantities.split(",");
        return names;
    }

    public static String[] splitIngredientQuantities(String recipeQuantities) {
        String[] quantities = recipeQuantities.split(",");
        return quantities;
    }

    public static String[] splitIngredientUnits(String recipeUnits) {
        String[] units = recipeUnits.split(",");
        return units;
    }
}
