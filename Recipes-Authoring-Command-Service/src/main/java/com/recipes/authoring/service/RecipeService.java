package com.recipes.authoring.service;

import java.util.Optional;
import java.util.UUID;

import com.recipes.authoring.common.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recipes.authoring.dto.RecipeDTO;
import com.recipes.authoring.exception.NotFoundException;
import com.recipes.authoring.model.Recipe;
import com.recipes.authoring.repo.RecipeH2Repository;
import com.recipes.event.RecipeProducer;

@Service
public class RecipeService {

    @Autowired
    private RecipeProducer producer;
    @Autowired
    private RecipeH2Repository recipeH2Repository;

    public RecipeDTO getRecipeById (String id) {
        Recipe recipe = findById(id);
        return RecipeDTO.builder()
                        .id(recipe.getId())
                        .type(recipe.getType())
                        .chef(recipe.getChef())
                        .name(recipe.getName())
                        .servings(recipe.getServings())
                        .ingredients(recipe.getIngredients())
                        .instructions(recipe.getInstructions())
                        .build();

    }

    public String createNewRecipe (RecipeDTO recipe) {
        Recipe recipeEntity = Recipe.builder()
                                    .id(UUID.randomUUID().toString())
                                    .type(recipe.getType())
                                    .chef(recipe.getChef())
                                    .name(recipe.getName())
                                    .servings(recipe.getServings())
                                    .ingredients(recipe.getIngredients())
                                    .instructions(recipe.getInstructions())
                                    .build();
        recipeH2Repository.save(recipeEntity);
        producer.sendRecipe(recipeEntity, OperationType.CREATE.value());
        return recipeEntity.getId();

    }

    private Recipe findById (String id) {
        Optional<Recipe> recipe = recipeH2Repository.findById(id);
        if (recipe.isEmpty()) {
            throw new NotFoundException(id);
        }
        return recipe.get();
    }

    public void deleteRecipeById (String id) {
        //Calling just to catch the not found exception
        findById(id);
        recipeH2Repository.deleteById(id);
        producer.sendRecipe(Recipe.builder().id(id).build(), OperationType.DELETE.value());
    }

    public void updateRecipe (String id, RecipeDTO recipeDTO) {
        Recipe recipe = findById(id);
        recipe.setChef(recipeDTO.getChef());
        recipe.setServings(recipeDTO.getServings());
        recipe.setName(recipeDTO.getName());
        recipe.setType(recipeDTO.getType());
        if (recipeDTO.getIngredients() != null) {
            recipe.setIngredients(recipeDTO.getIngredients());
        }
        if (recipeDTO.getInstructions() != null) {
            recipe.setInstructions(recipeDTO.getInstructions());
        }
        recipeH2Repository.save(recipe);
        producer.sendRecipe(recipe, OperationType.UPDATE.value());
    }
}
