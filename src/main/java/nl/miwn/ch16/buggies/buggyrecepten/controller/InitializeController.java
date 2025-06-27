package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.*;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

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

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
        loadRecipes();
        loadIngredients();
        loadCategories();
        loadAdminUsers();
    }

    private void loadRecipes() {
        Recipe eggs = new Recipe();
        Recipe toast = new Recipe();
        Recipe pasta = new Recipe();

        eggs.setName("Egg");
        toast.setName("Toast");
        pasta.setName("Pasta");

        eggs.setRecipeSteps("cook egg");
        toast.setRecipeSteps("toast bread");
        pasta.setRecipeSteps("boil pasta");

        eggs.setIngredients("egg");
        toast.setIngredients("bread");
        pasta.setIngredients("spaghetti");

        recipeRepository.save(eggs);
        recipeRepository.save(toast);
        recipeRepository.save(pasta);
    }

    private void loadIngredients() {
        Ingredient egg = new Ingredient();
        Ingredient bread = new Ingredient();
        Ingredient spaghetti = new Ingredient();

        egg.setName("Egg");
        bread.setName("Bread");
        spaghetti.setName("Spaghetti");

        ingredientRepository.save(egg);
        ingredientRepository.save(bread);
        ingredientRepository.save(spaghetti);
    }

    private void loadCategories() {
        Category simple = new Category();
        Category summer = new Category();

        simple.setName("simple");
        summer.setName("summer");

        List<Recipe> simpleList = recipeRepository.findAll();
        List<Recipe> summerlist = recipeRepository.findAllByNameContains("a");

        simple.setRecipes(simpleList);
        summer.setRecipes(summerlist);

        categoryRepository.save(simple);
        categoryRepository.save(summer);
    }

    private void loadAdminUsers() {
        AdminUser billy = new AdminUser();
        billy.setName("Billy");
        billy.setPassword(encoder.encode("test123"));
        adminUserRepository.save(billy);

        AdminUser harry = new AdminUser();
        harry.setName("Harry");
        harry.setPassword(encoder.encode("test456"));
        adminUserRepository.save(harry);
    }
}
