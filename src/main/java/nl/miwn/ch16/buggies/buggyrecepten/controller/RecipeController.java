package nl.miwn.ch16.buggies.buggyrecepten.controller;

/*
 * @Author: Joost Numan
 * this program controls all actions regarding the recipe class
 */

import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

@Controller

public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping({"/", "/homePage"})
    private String showHomePage(Model datamodel) {
        List<Recipe> allRecipes = recipeRepository.findAll();

        System.out.println("Dag Joost");

        datamodel.addAttribute("allRecipes", allRecipes);

        return "homePage";
    }

    @GetMapping("/recipeDetails/{recipeId}")
    private String showRecipeDetails(@PathVariable ("recipeId") Long recipeId, Model datamodel)  {



        return "recipeDetails";
    }


}

