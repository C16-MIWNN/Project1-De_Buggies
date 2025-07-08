package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marnix Ripke
 * This program describes any user of the application
 */

@Entity
public class AdminUser {

    @Id
    @GeneratedValue
    private Long adminId;

    private String name;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "adminuser_favorite_recipes",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> favoriteRecipes = new HashSet<>();

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(Set<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }
}
