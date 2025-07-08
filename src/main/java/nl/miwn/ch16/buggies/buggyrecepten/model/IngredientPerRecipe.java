package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.*;

/*
 * @Author: Joost Numan
 * This model describes the ingredients per recipe. It takes pre-existing ingredients and links them to recipes with
 * the unit and amount the recipe requires
 */

@Entity
public class IngredientPerRecipe {

    @Id @GeneratedValue
    private Long ingredientPerRecipeId;

    private double amount;

    private String unit;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private Ingredient ingredient;

    public Long getIngredientPerRecipeId() {
        return ingredientPerRecipeId;
    }

    public void setIngredientPerRecipeId(Long ingredientPerRecipeId) {
        this.ingredientPerRecipeId = ingredientPerRecipeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
