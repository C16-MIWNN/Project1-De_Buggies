package nl.miwn.ch16.buggies.buggyrecepten.controller;

/*
 * @Author: Joost Numan
 * this program controls all actions regarding the recipe class
 */

import jakarta.servlet.http.HttpServletRequest;
import nl.miwn.ch16.buggies.buggyrecepten.model.AdminUser;
import nl.miwn.ch16.buggies.buggyrecepten.model.Category;
import nl.miwn.ch16.buggies.buggyrecepten.model.Ingredient;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.AdminUserRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.CategoryRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import nl.miwn.ch16.buggies.buggyrecepten.service.NewRecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final NewRecipeService newRecipeService;
    private final AdminUserRepository adminUserRepository;


    public RecipeController(RecipeRepository recipeRepository,
                            CategoryRepository categoryRepository,
                            NewRecipeService newRecipeService) {

        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.newRecipeService = newRecipeService;
        this.adminUserRepository = adminUserRepository;
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

        datamodel.addAttribute("allRecipes", recipeRepository.findAll());
        datamodel.addAttribute("allCategories", categoryRepository.findAll());
        datamodel.addAttribute("formCategory", categoryToBeMade);
        datamodel.addAttribute("formIngredient", ingredientToBeMade);
        datamodel.addAttribute("formModalHidden", true);

        return "homePage";
    }

    @GetMapping("/recipe/detail/{name}")
    private String showRecipeDetails(@PathVariable("name") String name, Principal principal, Model datamodel) {
        Optional<Recipe> recipeOptional = recipeRepository.findByName(name);
        String userName = principal.getName();
        Optional<AdminUser> currentUser = adminUserRepository.findByName(userName);

        boolean recipeFavorited = false;


        if (recipeOptional.isPresent()) {

            if (currentUser.isPresent()) {
                if (recipeOptional.get().getFavoritedByAdmins().contains(currentUser.get())) {
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


    @GetMapping("/recipe/new")
    private String showNewRecipeForm(Model datamodel) {
        datamodel.addAttribute("formRecipe", new Recipe());
        datamodel.addAttribute("allCategories", categoryRepository.findAll());

        return "recipeForm";
    }

    @PostMapping("/recipe/save")
    private String saveOrUpdateRecipe(@ModelAttribute("formDesign") Recipe recipeToBeSaved,
                                      @RequestParam List<Long> categories,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        }

        List<Category> selectedCategories = categoryRepository.findAllById(categories);
        recipeToBeSaved.setCategories(selectedCategories);

        recipeRepository.save(recipeToBeSaved);

        return "redirect:/";
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

    @GetMapping("/recipe/delete/{recipeId}")
    private String deleteRecipe(@PathVariable("recipeId") Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            recipeRepository.deleteById(recipeId);
        }

        return "redirect:/";
    }

}