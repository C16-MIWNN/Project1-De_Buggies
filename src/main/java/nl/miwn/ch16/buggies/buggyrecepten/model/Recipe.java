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
    private boolean favorite;

    private String recipeSteps;
    private String ingredients;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private Set<AdminUser> favoritedByAdmins = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    public List<String> getListOfRecipeSteps() {
        return Arrays.asList(this.recipeSteps.split(";"));
    }

    public List<String> getListOfIngredients() {
        return Arrays.asList(this.ingredients.split(";"));
    }

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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(String recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return String.format(this.name);
    }
}
