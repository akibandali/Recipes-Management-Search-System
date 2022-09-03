package com.recipes.authoring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.recipes.authoring.dto.RecipeDTO;
import com.recipes.authoring.model.Recipe;
import com.recipes.authoring.repo.RecipeH2Repository;
import com.recipes.event.RecipeProducer;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {

    @Mock
    private RecipeProducer producer;
    @Mock
    private RecipeH2Repository recipeH2Repository;
    @InjectMocks
    private RecipeService recipeService;
    private static final String RECIPE_ID = "recipe1";

    @Before
    public void setUp () {
        doNothing().when(producer).sendRecipe(any(), any());

    }

    @Test
    public void testGetRecipeById () {
        Recipe testRecipe = testRecipe();
        doReturn(Optional.of(testRecipe)).when(recipeH2Repository).findById(RECIPE_ID);
        RecipeDTO actual = recipeService.getRecipeById(RECIPE_ID);
        assertThat(actual).isNotNull();
        assertRecipes(actual, testRecipe);
    }

    @Test
    public void testCreateNewRecipe () {
        RecipeDTO recipeDTO = testRecipeDTO();
        doReturn(Recipe.builder().build()).when(recipeH2Repository).save(any());
        String actual = recipeService.createNewRecipe(recipeDTO);
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEqualTo(recipeDTO.getId());

    }

    @Test
    public void deleteRecipeById () {
        doNothing().when(recipeH2Repository).deleteById(RECIPE_ID);
        recipeService.deleteRecipeById(RECIPE_ID);
        Mockito.verify(recipeH2Repository, times(1)).deleteById(RECIPE_ID);
    }

    @Test
    public void testUpdateRecipe () {
        RecipeDTO recipeDTO = testRecipeDTO();
        recipeDTO.setServings(5);
        recipeDTO.setType("Veg");
        Recipe expected = testRecipe();
        expected.setServings(5);
        expected.setType("Veg");
        doReturn(Optional.of(testRecipe())).when(recipeH2Repository).findById(RECIPE_ID);
        doReturn(Recipe.builder().build()).when(recipeH2Repository).save(any());
        recipeService.updateRecipe(recipeDTO.getId(), recipeDTO);
        Mockito.verify(recipeH2Repository, times(1)).save(expected);
    }

    private void assertRecipes (RecipeDTO actual, Recipe expected) {
        assertThat(actual.getType()).isEqualTo(expected.getType());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getServings()).isEqualTo(expected.getServings());
        assertThat(actual.getChef()).isEqualTo(expected.getChef());
        assertThat(actual.getIngredients()).isEqualTo(expected.getIngredients());
        assertThat(actual.getInstructions()).isEqualTo(expected.getInstructions());

    }

    public static Recipe testRecipe () {
        return Recipe.builder()
            .id(RECIPE_ID)
            .type("Non-Veg")
            .chef("Jordon")
            .name("Biryani")
            .servings(10)
            .ingredients(Arrays.asList("Rice", "chicken", "spices"))
            .instructions(Arrays.asList("Marinate chicken", "Bake chicken in oven"))
            .build();
    }

    public static RecipeDTO testRecipeDTO () {
        return RecipeDTO.builder()
            .id(RECIPE_ID)
            .type("Non-Veg")
            .chef("Jordon")
            .name("Biryani")
            .servings(10)
            .ingredients(Arrays.asList("Rice", "chicken", "spices"))
            .instructions(Arrays.asList("Marinate chicken", "Bake chicken in oven"))
            .build();
    }
}
