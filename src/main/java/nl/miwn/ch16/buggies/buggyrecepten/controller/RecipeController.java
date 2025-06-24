package nl.miwn.ch16.buggies.buggyrecepten.controller;

/*
 * @Author: Joost Numan
 * this program controls all actions regarding the recipe class
 */

import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RecipeController {
    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping({"/", "/homePage"})
    private String showHomePage(Model datamodel) {
        datamodel.addAttribute("allRecipes", recipeRepository.findAll());

        return "homePage";
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
