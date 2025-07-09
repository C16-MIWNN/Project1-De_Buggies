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
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public InitializeController(IngredientRepository ingredientRepository,
                                RecipeRepository recipeRepository,
                                CategoryRepository categoryRepository,
                                UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (recipeRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {
        loadUsers();
        loadRecipes();
        loadIngredients();
        loadCategories();
    }

    private void loadRecipes() {
        Recipe eggs = new Recipe();
        Recipe toast = new Recipe();
        Recipe pasta = new Recipe();
        Recipe applepie = new Recipe();

        eggs.setName("Egg");
        toast.setName("Toast");
        pasta.setName("Pasta");
        applepie.setName("Apple pie");

        eggs.setRecipeStepsList(Arrays.asList("boil water", "cook egg"));
        toast.setRecipeStepsList(Arrays.asList("get bread", "toast bread"));
        pasta.setRecipeStepsList(Arrays.asList("boil water", "cook pasta"));
        applepie.setRecipeStepsList(Arrays.asList("make it", "bake it", "eat it"));

        eggs.setIngredientsList(Arrays.asList("water", "egg"));
        toast.setIngredientsList(Arrays.asList("water", "bread"));
        pasta.setIngredientsList(Arrays.asList("water", "spaghetti"));
        applepie.setIngredientsList(Arrays.asList("apples", "sugar", "flour", "egg", "nutmeg", "cinnamon"));

        eggs.setCreator((NormalUser) userRepository.findByName("Harry").get());
        toast.setCreator((NormalUser) userRepository.findByName("Linda").get());
        pasta.setCreator((NormalUser) userRepository.findByName("John").get());
        applepie.setCreator((NormalUser) userRepository.findByName("Peter").get());

        recipeRepository.saveAll(List.of(eggs, toast, pasta, applepie));
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
        Category winter = new Category();
        Category dessert = new Category();


        simple.setName("Simple");
        summer.setName("Summer");
        winter.setName("Winter");
        dessert.setName("Dessert");


        // Retrieve existing recipes
        List<Recipe> allRecipes = recipeRepository.findAll();

        // Set relationships from owning side (Recipe)
        for (Recipe recipe : allRecipes) {
            recipe.getCategories().add(simple);
            recipe.getCategories().add(summer);
            recipe.getCategories().add(dessert);

            // Optional: set inverse side
            simple.getRecipes().add(recipe);
            summer.getRecipes().add(recipe);
            dessert.getRecipes().add(recipe);
        }

        // Save categories first (optional if cascading is enabled)
        categoryRepository.save(simple);
        categoryRepository.save(summer);
        categoryRepository.save(dessert);

        // Save recipes (important — Recipe owns the relationship)
        recipeRepository.saveAll(allRecipes);
    }

    private void loadUsers() {
        AdminUser admin = new AdminUser();
        admin.setName("admin");
        admin.setPassword(encoder.encode("123"));

        NormalUser harry = new NormalUser();
        harry.setName("Harry");
        harry.setPassword(encoder.encode("test456"));

        NormalUser linda = new NormalUser();
        linda.setName("Linda");
        linda.setPassword(encoder.encode("789"));

        NormalUser john = new NormalUser();
        john.setName("John");
        john.setPassword(encoder.encode("001"));

        NormalUser peter = new NormalUser();
        peter.setName("Peter");
        peter.setPassword(encoder.encode("002"));

        userRepository.saveAll(List.of(admin, harry, linda, john, peter));
    }
}
