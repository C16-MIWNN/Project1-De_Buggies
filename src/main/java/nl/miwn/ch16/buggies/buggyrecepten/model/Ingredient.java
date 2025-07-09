package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

/*
 * @Author: Joost Numan
 * This program describes an ingredient that can be used in recipes
 */

@Entity
public class Ingredient {

    @Id @GeneratedValue
    private Long ingredientId;

    private String name;

    @OneToMany
    private List<IngredientPerRecipe> ingredientPerRecipeList;

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long designId) {
        this.ingredientId = designId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientPerRecipe> getIngredientPerRecipeList() {
        return ingredientPerRecipeList;
    }

    public void setIngredientPerRecipeList(List<IngredientPerRecipe> ingredientPerRecipeList) {
        this.ingredientPerRecipeList = ingredientPerRecipeList;
    }
}
