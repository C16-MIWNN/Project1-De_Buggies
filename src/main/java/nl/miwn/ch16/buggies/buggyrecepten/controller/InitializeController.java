package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.*;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

/*
 * @Author: Joost Numan
 * Initializes all the data required to test the website
 */

@Controller
public class InitializeController {
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final AdminUserRepository adminUserRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public InitializeController(IngredientRepository ingredientRepository,
                                RecipeRepository recipeRepository,
                                CategoryRepository categoryRepository,
                                AdminUserRepository adminUserRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.adminUserRepository = adminUserRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (recipeRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {
        loadAdminUsers();
        loadRecipes();
        loadIngredients();
        loadCategories();
    }

    private void loadRecipes() {
        Recipe eggs = new Recipe();
        Recipe toast = new Recipe();
        Recipe pasta = new Recipe();

        eggs.setName("Egg");
        toast.setName("Toast");
        pasta.setName("Pasta");

        eggs.setRecipeStepsList(Arrays.asList("boil water", "cook egg"));
        toast.setRecipeStepsList(Arrays.asList("get bread", "toast bread"));
        pasta.setRecipeStepsList(Arrays.asList("boil water", "cook pasta"));

        eggs.setIngredientsList(Arrays.asList("water", "egg"));
        toast.setIngredientsList(Arrays.asList("water", "bread"));
        pasta.setIngredientsList(Arrays.asList("water", "spaghetti"));

        eggs.setCreator(adminUserRepository.findByName("Billy").get());
        toast.setCreator(adminUserRepository.findByName("Billy").get());
        pasta.setCreator(adminUserRepository.findByName("Billy").get());

        recipeRepository.saveAll(List.of(eggs, toast, pasta));
    }

    private void loadIngredients() {
        Ingredient egg = new Ingredient();
        Ingredient bread = new Ingredient();
        Ingredient spaghetti = new Ingredient();

        egg.setName("Egg");
        bread.setName("Bread");
        spaghetti.setName("Spaghetti");

        ingredientRepository.saveAll(List.of(egg, bread, spaghetti));
    }

    private void loadCategories() {
        // Create categories
        Category simple = new Category();
        Category summer = new Category();

        simple.setName("Simple");
        summer.setName("Summer");

        // Retrieve existing recipes
        List<Recipe> allRecipes = recipeRepository.findAll();

        // Set relationships from owning side (Recipe)
        for (Recipe recipe : allRecipes) {
            recipe.getCategories().add(simple);
            recipe.getCategories().add(summer);

            // Optional: set inverse side
            simple.getRecipes().add(recipe);
            summer.getRecipes().add(recipe);
        }

        // Save categories first (optional if cascading is enabled)
        categoryRepository.save(simple);
        categoryRepository.save(summer);

        // Save recipes (important — Recipe owns the relationship)
        recipeRepository.saveAll(allRecipes);
    }

    private void loadAdminUsers() {
        AdminUser billy = new AdminUser();
        billy.setName("Billy");
        billy.setPassword(encoder.encode("test123"));

        AdminUser harry = new AdminUser();
        harry.setName("Harry");
        harry.setPassword(encoder.encode("test456"));

        adminUserRepository.saveAll(List.of(billy, harry));
    }
}
