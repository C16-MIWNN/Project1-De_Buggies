package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Controller;

import java.util.Set;

/*
 * @Author: Joost Numan
 * this class describes an ingredient linked to a recipe and the necessary unit of measurement
 * and the quantity needed in the recipe
 */

@Entity
public class IngredientPerRecipe {

    @Id @GeneratedValue
    private Long IngredientPerRecipeId;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Ingredient ingredient;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Recipe recipe;

    private String unitOfMeasurement;
    private Double quantity;

    public Long getIngredientPerRecipeId() {
        return IngredientPerRecipeId;
    }

    public void setIngredientPerRecipeId(Long ingredientPerRecipeId) {
        IngredientPerRecipeId = ingredientPerRecipeId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
