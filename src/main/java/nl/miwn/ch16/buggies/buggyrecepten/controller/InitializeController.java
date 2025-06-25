package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.Ingredient;
import nl.miwn.ch16.buggies.buggyrecepten.model.IngredientPerRecipe;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientPerRecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/*
 * @Author: Joost Numan
 * Initializes all the data required to test the website
 */

@Controller
public class InitializeController {
    private final IngredientPerRecipeRepository ingredientPerRecipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public InitializeController(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, IngredientPerRecipeRepository ingredientPerRecipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientPerRecipeRepository = ingredientPerRecipeRepository;
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
        loadIngredientsPerRecipe();
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

        egg.setQuantity(2.0);
        bread.setQuantity(1.0);
        spaghetti.setQuantity(100.0);

        egg.setUnit("zcs");
        bread.setUnit("slices");
        spaghetti.setUnit("grams");

        ingredientRepository.save(egg);
        ingredientRepository.save(bread);
        ingredientRepository.save(spaghetti);
    }

    private void loadIngredientsPerRecipe() {
        IngredientPerRecipe eggsIngredient1 = new IngredientPerRecipe();
        IngredientPerRecipe toastIngredient1 = new IngredientPerRecipe();
        IngredientPerRecipe pastaIngredient1 = new IngredientPerRecipe();

        eggsIngredient1.setIngredient(ingredientRepository.findByName("Egg").get());
        toastIngredient1.setIngredient(ingredientRepository.findByName("Bread").get());
        pastaIngredient1.setIngredient(ingredientRepository.findByName("Spaghetti").get());

        eggsIngredient1.setRecipe(recipeRepository.findByName("Egg").get());
        toastIngredient1.setRecipe(recipeRepository.findByName("Toast").get());
        pastaIngredient1.setRecipe(recipeRepository.findByName("Pasta").get());

        ingredientPerRecipeRepository.save(eggsIngredient1);
        ingredientPerRecipeRepository.save(toastIngredient1);
        ingredientPerRecipeRepository.save(pastaIngredient1);
    }
}
