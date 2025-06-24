package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.*;

import java.util.Set;

/*
 * @Author: Joost Numan
 * This prgram describes a recipe that is used as the main entity of the website
 */

@Entity
public class Recipe {

    @Id @GeneratedValue
    private Long recipeId;

    private String name;
    private String recipeSteps;

    @ManyToMany
    private Set<Ingredient> ingredients;

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long designId) {
        this.recipeId = designId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(String recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
