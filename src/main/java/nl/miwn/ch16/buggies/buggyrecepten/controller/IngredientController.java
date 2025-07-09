package nl.miwn.ch16.buggies.buggyrecepten.controller;

import nl.miwn.ch16.buggies.buggyrecepten.model.Ingredient;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.IngredientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Marnix Ripke
 * Handle requests related to ingredients
 */

@Controller
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping("/ingredient/new")
    private String saveOrUpdateIngredient(@ModelAttribute("formDesign") Ingredient ingredientToBeSaved,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
        } else {
            ingredientRepository.save(ingredientToBeSaved);
        }

        return "redirect:/";
    }
}
