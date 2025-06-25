package nl.miwn.ch16.buggies.buggyrecepten.controller;

/*
 * @Author: Joost Numan
 * this program controls all actions regarding the recipe class
 */

import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.CategoryRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    public RecipeController(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    private String setupRecipeDetail(Model datamodel, Recipe recipeToShow, Recipe formRecipe) {
        datamodel.addAttribute("recipe", recipeToShow);
        datamodel.addAttribute("formRecipe", formRecipe);

        return "RecipeDetails";
    }

    @GetMapping({"/", "/homePage"})
    private String showHomePage(Model datamodel) {
        List<Recipe> allRecipes = recipeRepository.findAll();

        datamodel.addAttribute("allRecipes", recipeRepository.findAll());
        datamodel.addAttribute("allCategories", categoryRepository.findAll());

        return "homePage";
    }

    @GetMapping("/recipe/detail/{recipeId}")
    private String showRecipeDetails(@PathVariable("recipeId") Long recipeId, Model datamodel) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        return recipeOptional.map(recipe -> setupRecipeDetail(datamodel,
                recipe, recipe)).orElse("redirect:/homePage");
    }

    @GetMapping("/recipe/new")
    private String showNewRecipeForm(Model datamodel) {
        datamodel.addAttribute("formRecipe", new Recipe());

        return "newRecipeForm";
    }

    @PostMapping("/recipe/new")
    private String saveOrUpdateRecipe(@ModelAttribute("formDesign") Recipe recipeToBeSaved,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        } else {
            recipeRepository.save(recipeToBeSaved);
        }

        return "redirect:/";
    }
}

