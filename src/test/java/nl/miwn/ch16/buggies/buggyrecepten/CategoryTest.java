package nl.miwn.ch16.buggies.buggyrecepten;

import nl.miwn.ch16.buggies.buggyrecepten.controller.CategoryController;
import nl.miwn.ch16.buggies.buggyrecepten.model.Category;
import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.CategoryRepository;
import nl.miwn.ch16.buggies.buggyrecepten.repositories.RecipeRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.mockito.Mockito.mock;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */

public class CategoryTest {

    private CategoryRepository mockCategoryRepository;
    private RecipeRepository mockRecipeRepository;
    private CategoryController controller;

    @BeforeEach
    void setUp() {
        mockCategoryRepository = mock(CategoryRepository.class);
        mockRecipeRepository = mock(RecipeRepository.class);
        controller = new CategoryController(mockCategoryRepository, mockRecipeRepository);
    }

    @Test
    @Disabled
    @DisplayName("Should check length of category list")
    void shouldCheckLengthOfCategoryList() {
        // Arrange
        Recipe recipeToCheck = new Recipe();

        Category category1 = new Category();
        Category category2 = new Category();

        int categoryAmount = 2;

        List<Category> categories = List.of(category1, category2);

        // Act
        recipeToCheck.setCategories(categories);

        int categoriesInList = recipeToCheck.getCategories().size();

        // Assert
        Assertions.assertEquals(categoryAmount, categoriesInList);
    }

    @Test
    @Disabled
    @DisplayName("Checks if category is given")
    void checksIfCategoryIsGiven() {
        // Arrange
        Recipe recipeToCheck = new Recipe();

        Category category1 = new Category();
        Category category2 = new Category();

        List<Category> categories = List.of(category1, category2);

        // Act
        recipeToCheck.setCategories(categories);

        // Assert
        Assertions.assertFalse(recipeToCheck.getCategories().isEmpty());
    }

    @Test
    @DisplayName("Checks if category is given with controller method")
    void checksIfCategoryIsGivenWithControllerMethod() {
        // Arrange
        String category1 = "yes";
        String category2 = "maybe";
        String category3 = "";

        // Act
        Boolean category1Check = controller.isCategoryGiven(category1);
        Boolean category2Check = controller.isCategoryGiven(category2);
        Boolean category3Check = controller.isCategoryGiven(category3);

        // Assert
        Assertions.assertAll(() -> Assertions.assertTrue(category1Check),
                () -> Assertions.assertTrue(category2Check),
                () -> Assertions.assertFalse(category3Check));
    }
}
