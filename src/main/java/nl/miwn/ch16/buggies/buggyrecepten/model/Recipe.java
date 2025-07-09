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

    @ManyToOne
    @JoinColumn(name = "creator_normaluser_id", nullable = false)
    private NormalUser creator;

    private List<String> recipeStepsList;
    private List<String> ingredientsList;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private Set<NormalUser> favoritedByUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @Override
    public String toString() {
        return String.format(this.name);
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

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<String> getRecipeStepsList() {
        return recipeStepsList;
    }

    public void setRecipeStepsList(List<String> recipeStepsList) {
        this.recipeStepsList = recipeStepsList;
    }

    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public NormalUser getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = (NormalUser) creator;
    }

    public Set<NormalUser> getFavoritedByUsers() {
        return favoritedByUsers;
    }

    public void setFavoritedByUsers(Set<NormalUser> favoritedByUsers) {
        this.favoritedByUsers = favoritedByUsers;
    }
}
