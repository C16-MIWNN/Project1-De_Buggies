package nl.miwn.ch16.buggies.buggyrecepten.dto;

import nl.miwn.ch16.buggies.buggyrecepten.model.Category;

import java.util.List;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */

public class NewRecipeDTO {

    private List<String> units;
    private List<String> amounts;
    private List<String> ingredientsList;

    private String recipeName;
    private List<String> recipeStepsList;

    private List<Category> categories;

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public List<String> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<String> amounts) {
        this.amounts = amounts;
    }

    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<String> getRecipeStepsList() {
        return recipeStepsList;
    }

    public void setRecipeStepsList(List<String> recipeStepsList) {
        this.recipeStepsList = recipeStepsList;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
