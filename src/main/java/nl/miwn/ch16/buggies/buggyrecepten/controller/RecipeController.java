package nl.miwn.ch16.buggies.buggyrecepten.controller;

/*
 * @Author: Joost Numan
 * this program controls all actions regarding the recipe class
 */

import jakarta.servlet.http.HttpServletRequest;
import nl.miwn.ch16.buggies.buggyrecepten.dto.NewRecipeDTO;
import nl.miwn.ch16.buggies.buggyrecepten.model.*;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.CategoryRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.UserRepository;
import nl.miwn.ch16.buggies.buggyrecepten.service.NewRecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final NewRecipeService newRecipeService;
    private final UserRepository userRepository;


    public RecipeController(RecipeRepository recipeRepository,
                            CategoryRepository categoryRepository,
                            NewRecipeService newRecipeService,
                            UserRepository userRepository) {

        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.newRecipeService = newRecipeService;
        this.userRepository = userRepository;
    }

    private String setupRecipeDetail(Model datamodel, Recipe formRecipe) {
        datamodel.addAttribute("formRecipe", formRecipe);

        return "RecipeDetails";
    }

    @GetMapping({"/", "/homePage"})
    private String showHomePage(@ModelAttribute("formCategory") Category categoryToBeMade,
                                @ModelAttribute("formIngredient") Ingredient ingredientToBeMade,
                                Model datamodel) {
        List<Recipe> allRecipes = recipeRepository.findAll();

        Collections.shuffle(allRecipes);

        List<Recipe> topThreeRecipes = allRecipes.stream().limit(3).toList();

        datamodel.addAttribute("allRecipes", topThreeRecipes);
        datamodel.addAttribute("allCategories", categoryRepository.findAll());
        datamodel.addAttribute("formCategory", categoryToBeMade);
        datamodel.addAttribute("formIngredient", ingredientToBeMade);
        datamodel.addAttribute("formModalHidden", true);

        return "homePage";
    }

    @GetMapping({"/recipe/all-recipes"})
    private String showAllRecipesPage(@ModelAttribute("formCategory") Category categoryToBeMade,
                                @ModelAttribute("formIngredient") Ingredient ingredientToBeMade,
                                Model datamodel) {
        List<Recipe> allRecipes = recipeRepository.findAll();

        datamodel.addAttribute("allRecipes", recipeRepository.findAll());
        datamodel.addAttribute("allCategories", categoryRepository.findAll());
        datamodel.addAttribute("formCategory", categoryToBeMade);
        datamodel.addAttribute("formIngredient", ingredientToBeMade);
        datamodel.addAttribute("formModalHidden", true);

        return "allRecipesPage";
    }

    @GetMapping("/recipe/detail/{name}")
    private String showRecipeDetails(@PathVariable("name") String name, Principal principal, Model datamodel) {
        Optional<Recipe> recipeOptional = recipeRepository.findByName(name);
        String userName = principal.getName();
        Optional<User> currentUser = userRepository.findByName(userName);

        boolean recipeFavorited = false;


        if (recipeOptional.isPresent()) {

            if (currentUser.isPresent()) {
                if (recipeOptional.get().getFavoritedByUsers().contains(currentUser.get())) {
                    recipeFavorited = true;
                }
            }

            Recipe recipe = recipeOptional.get();
            datamodel.addAttribute("recipeToBeShown", recipe);
            datamodel.addAttribute("recipeFavorited", recipeFavorited);

            return setupRecipeDetail(datamodel, recipe);
        } else {
            return "redirect:/homePage";
        }
    }

    @GetMapping("/recipe/new")
    private String showNewRecipeDTOForm(Model datamodel) {
        NewRecipeDTO formRecipe = new NewRecipeDTO();

        datamodel.addAttribute("formRecipe", formRecipe);
        datamodel.addAttribute("allCategories", categoryRepository.findAll());

        return "newRecipeForm";
    }

    @PostMapping("/recipe/save")
    private String saveOrUpdateRecipeDTO(@ModelAttribute("formRecipe") NewRecipeDTO recipeToBeSaved,
                                         @RequestParam List<Long> categories,
                                         BindingResult bindingResult,
                                         Principal principal){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        }

        Optional<User> creator = userRepository.findByName(principal.getName());
        creator.ifPresent(User -> newRecipeService.saveRecipe(recipeToBeSaved, (NormalUser) User));

        return "redirect:/recipe/all-recipes";
    }

    @GetMapping("/recipe/edit/{name}")
    private String editRecipe(@PathVariable("name") String name, Model datamodel) {
        Optional<Recipe> recipeOptional = recipeRepository.findByName(name);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            datamodel.addAttribute("formRecipe", recipe);
            datamodel.addAttribute("allCategories", categoryRepository.findAll());
            return "recipeForm";
        }

        return "redirect:/";
    }

    @PostMapping("/recipe/favorite")
    public String toggleFavorite(@ModelAttribute("formRecipe") Recipe recipeToBeShown,
                                 Model datamodel,
                                 HttpServletRequest request,
                                 Principal principal) {
        Long recipeId = recipeToBeShown.getRecipeId();

        if (recipeId != null) {
            String username = principal.getName();
            newRecipeService.toggleFavorite(recipeId, username);

            datamodel.addAttribute("recipeToBeShown", recipeToBeShown);

            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        return "redirect:/";
    }

    @GetMapping("/recipe/delete/{recipeId}")
    private String deleteRecipe(@PathVariable("recipeId") Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            recipeRepository.deleteById(recipeId);
        }

        return "redirect:/";
    }

}