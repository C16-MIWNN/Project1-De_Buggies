package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.Category;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Marnix Ripke
 * Handles requests related to categories
 */

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
}
