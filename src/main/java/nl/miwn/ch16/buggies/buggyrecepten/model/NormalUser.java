package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 * @author Marnix Ripke
 * This program desribes the actual user of the program
 */

@Entity
@DiscriminatorValue("NORMAL")
public class NormalUser extends User {

    public NormalUser() {
        this.setRoles(Set.of("ROLE_NORMAL"));
    }

    @ManyToMany
    @JoinTable(
            name = "normaluser_favorite_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> favoriteRecipes = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes = new ArrayList<>();

    public Set<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(Set<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}