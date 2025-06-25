package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.*;

import java.util.*;

/*
 * @Author: Joost Numan
 * This program describes a recipe that is used as the main entity of the website
 */

@Entity
public class Recipe {

    @Id @GeneratedValue
    private Long recipeId;

    private String name;
    private String recipeSteps;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<IngredientPerRecipe> ingredientsPerRecipe;

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

    public void setCategories(List<Category> selectedTeachers) {
        this.categories = selectedTeachers;
    }

    public List<Category> getCategories() {
        return categories;
    }

}
