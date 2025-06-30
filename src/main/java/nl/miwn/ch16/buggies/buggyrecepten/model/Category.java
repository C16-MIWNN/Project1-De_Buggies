package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marnix Ripke
 * This program describes a category that can be added to a recipe
 */
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long CategoryId;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Recipe> recipes = new ArrayList<>();

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long categoryId) {
        CategoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return name;
    }

}
