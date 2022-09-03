/*
package com.recipes.authoring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.authoring.dto.RecipeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.recipes.authoring.service.RecipeServiceTest.testRecipeDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerITTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGetRecipe () throws Exception {
        // Create New Recipe
        RecipeDTO recipeDTO = testRecipeDTO();
        ResultActions resultActions = mockMvc.perform(post("/v1/authoring/recipe").contentType("application/json")
            .content(convertToString(recipeDTO)))
            .andExpect(status().isCreated());

        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        Assertions.assertEquals("application/json", response.getContentType());
        String recipeId = response.getContentAsString();
        assertThat(recipeId).isNotNull();

        // get Recipe
        ResultActions getResultActions = mockMvc.perform(get("/v1/authoring/recipe/" + recipeId).contentType("application/json"))
            .andExpect(status().isOk());

        RecipeDTO testRecipeDTO = testRecipeDTO();
        testRecipeDTO.setId(recipeId);

        MockHttpServletResponse getResponse = getResultActions.andReturn().getResponse();
        Assertions.assertEquals("application/json", getResponse.getContentType());
        String recipe = getResponse.getContentAsString();
        assertThat(recipe).isEqualTo(convertToString(testRecipeDTO));
    }

    @Test
    public void testUpdateRecipe () throws Exception {
        // Create New Recipe
        RecipeDTO recipeDTO = testRecipeDTO();
        String recipeId = createRecipe(recipeDTO);
        recipeDTO.setName("changed");

        // update
        mockMvc.perform(put("/v1/authoring/recipe/" + recipeId).contentType("application/json").content(convertToString(recipeDTO)))
            .andExpect(status().isNoContent());

        // get Recipe
        ResultActions getResultActions = mockMvc.perform(get("/v1/authoring/recipe/" + recipeId).contentType("application/json"))
            .andExpect(status().isOk());

        MockHttpServletResponse getResponse = getResultActions.andReturn().getResponse();
        Assertions.assertEquals("application/json", getResponse.getContentType());
        String recipe = getResponse.getContentAsString();
        recipeDTO.setId(recipeId);
        assertThat(recipe).isEqualTo(convertToString(recipeDTO));
    }

    @Test
    public void testDeleteRecipe () throws Exception {
        // Create New Recipe
        RecipeDTO recipeDTO = testRecipeDTO();
        String recipeId = createRecipe(recipeDTO);
        recipeDTO.setName("changed");

        // delete Recipe
        ResultActions getResultActions = mockMvc.perform(delete("/v1/authoring/recipe/" + recipeId).contentType("application/json"))
            .andExpect(status().isNoContent());

    }

    private String createRecipe (RecipeDTO recipeDTO) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/v1/authoring/recipe").contentType("application/json")
            .content(convertToString(recipeDTO)))
            .andExpect(status().isCreated());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        Assertions.assertEquals("application/json", response.getContentType());
        return response.getContentAsString();
    }

    private String convertToString (RecipeDTO recipeDTO) {
        try {
            return objectMapper.writeValueAsString(recipeDTO);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
*/
