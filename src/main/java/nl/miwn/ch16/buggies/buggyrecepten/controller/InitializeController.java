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
        Recipe applepie = new Recipe();
        Recipe salad = new Recipe();
        Recipe soup = new Recipe();
        Recipe pancakes = new Recipe();
        Recipe burger = new Recipe();

        eggs.setName("Egg");
        toast.setName("Toast");
        pasta.setName("Pasta");
        applepie.setName("Apple pie");
        salad.setName("Salad");
        soup.setName("Soup");
        pancakes.setName("Pancakes");
        burger.setName("Burger");

        eggs.setRecipeStepsList(Arrays.asList("boil water", "cook egg"));
        toast.setRecipeStepsList(Arrays.asList("get bread", "toast bread"));
        pasta.setRecipeStepsList(Arrays.asList("boil water", "cook pasta"));
        applepie.setRecipeStepsList(Arrays.asList("make it", "bake it", "eat it"));
        salad.setRecipeStepsList(Arrays.asList("chop veggies", "mix", "add dressing"));
        soup.setRecipeStepsList(Arrays.asList("boil broth", "add vegetables", "simmer"));
        pancakes.setRecipeStepsList(Arrays.asList("mix ingredients", "fry batter", "flip", "serve"));
        burger.setRecipeStepsList(Arrays.asList("grill patty", "toast bun", "assemble", "serve"));

        eggs.setIngredientsList(Arrays.asList("water", "egg"));
        toast.setIngredientsList(Arrays.asList("water", "bread"));
        pasta.setIngredientsList(Arrays.asList("water", "spaghetti"));
        applepie.setIngredientsList(Arrays.asList("apples", "sugar", "flour", "egg", "nutmeg", "cinnamon"));
        salad.setIngredientsList(Arrays.asList("lettuce", "tomato", "cucumber", "olive oil", "lemon"));
        soup.setIngredientsList(Arrays.asList("broth", "carrots", "onion", "celery"));
        pancakes.setIngredientsList(Arrays.asList("flour", "milk", "egg", "sugar", "butter"));
        burger.setIngredientsList(Arrays.asList("bun", "beef", "lettuce", "cheese", "tomato"));

        eggs.setCreator(adminUserRepository.findByName("Billy").get());
        toast.setCreator(adminUserRepository.findByName("Billy").get());
        pasta.setCreator(adminUserRepository.findByName("Billy").get());
        applepie.setCreator(adminUserRepository.findByName("Harry").get());
        salad.setCreator(adminUserRepository.findByName("Harry").get());
        soup.setCreator(adminUserRepository.findByName("Billy").get());
        pancakes.setCreator(adminUserRepository.findByName("Harry").get());
        burger.setCreator(adminUserRepository.findByName("Billy").get());

        recipeRepository.saveAll(List.of(
                eggs, toast, pasta, applepie, salad, soup, pancakes, burger
        ));
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
        Category simple = new Category();
        Category summer = new Category();
        Category winter = new Category();
        Category dessert = new Category();
        Category healthy = new Category();
        Category comfort = new Category();

        simple.setName("Simple");
        summer.setName("Summer");
        winter.setName("Winter");
        dessert.setName("Dessert");
        healthy.setName("Healthy");
        comfort.setName("Comfort Food");

        List<Recipe> allRecipes = recipeRepository.findAll();

        for (Recipe recipe : allRecipes) {
            recipe.getCategories().add(simple);

            if (recipe.getName().equals("Apple pie") || recipe.getName().equals("Pancakes")) {
                recipe.getCategories().add(dessert);
                dessert.getRecipes().add(recipe);
            }

            if (recipe.getName().equals("Salad") || recipe.getName().equals("Soup")) {
                recipe.getCategories().add(healthy);
                healthy.getRecipes().add(recipe);
            }

            if (recipe.getName().equals("Burger") || recipe.getName().equals("Pasta")) {
                recipe.getCategories().add(comfort);
                comfort.getRecipes().add(recipe);
            }

            recipe.getCategories().add(summer); // All get summer for demo
            simple.getRecipes().add(recipe);
            summer.getRecipes().add(recipe);
        }

        categoryRepository.saveAll(List.of(simple, summer, winter, dessert, healthy, comfort));
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
