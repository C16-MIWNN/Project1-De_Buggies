package nl.miwn.ch16.buggies.buggyrecepten.dto;

/*
 * @Author: Joost Numan
 * this DTO describes an object that can receive the information for multiple classes to later be split into individual
 * objects
 */

public class NewRecipeDTO {

    private String recipeName;
    private String recipeSteps;

    private String ingredientNames;

    private String ingredientQuantities;
    private String ingredientUnitsOfMeasurement;

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(String recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public String getIngredientNames() {
        return ingredientNames;
    }

    public void setIngredientNames(String ingredientName) {
        this.ingredientNames = ingredientName;
    }

    public String getIngredientQuantities() {
        return ingredientQuantities;
    }

    public void setIngredientQuantities(String ingredientQuantities) {
        this.ingredientQuantities = ingredientQuantities;
    }

    public String getIngredientUnitsOfMeasurement() {
        return ingredientUnitsOfMeasurement;
    }

    public void setIngredientUnitsOfMeasurement(String ingredientUnitsOfMeasurement) {
        this.ingredientUnitsOfMeasurement = ingredientUnitsOfMeasurement;
    }
}
