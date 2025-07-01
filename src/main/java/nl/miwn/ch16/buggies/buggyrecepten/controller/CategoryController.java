package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.Category;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.CategoryRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Marnix Ripke
 * Handles requests related to categories
 */

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public CategoryController(CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/category/new")
    private String showNewRecipeForm(Model datamodel) {
        datamodel.addAttribute("formCategory", new Category());

        return "newCategoryForm";
    }

    @PostMapping("/category/new")
    private String saveOrUpdateRecipe(@ModelAttribute("formDesign") Category categoryToBeSaved,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        } else {
            categoryRepository.save(categoryToBeSaved);
        }

        return "redirect:/";
    }

    @GetMapping("/category")
    private String showAllCategories(Model datamodel) {
        datamodel.addAttribute("allCategories", categoryRepository.findAll());

        return "categories";
    }

    @GetMapping("/category/search-by-category")
    public String searchByCategory(@RequestParam(required = false) String category, Model model) {
        List<Recipe> results = new ArrayList<>();
        String errorMessage = null;

        if (category != null && !category.isEmpty()) {
            List<Category> searchCategory = categoryRepository.findByName(category);

            if (!searchCategory.isEmpty()) {
                results = recipeRepository.findAllByCategories(searchCategory);
                if (results.isEmpty()) {
                    errorMessage = "This category does not contain any recipes";
                } else {
                    model.addAttribute("searchCategory", searchCategory.get(0));
                }
            } else {
                errorMessage = "This category does not contain any recipes";
            }
        } else {
            errorMessage = "This category does not exist or there was no category selected";
        }

        model.addAttribute("recipes", results);
        model.addAttribute("allCategories", categoryRepository.findAll());
        model.addAttribute("errorMessage", errorMessage);

        return "recipesPerCategory";
    }
}
