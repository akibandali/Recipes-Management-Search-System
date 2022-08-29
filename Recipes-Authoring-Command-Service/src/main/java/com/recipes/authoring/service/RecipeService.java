package com.recipes.authoring.service;

import java.util.Optional;
import java.util.UUID;
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
        producer.sendRecipe(recipeEntity, "create");
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
        recipeH2Repository.deleteById(id);
        producer.sendRecipe(Recipe.builder().id(id).build(), "delete");
    }

    public void updateRecipe (String id, RecipeDTO recipeDTO) {
        Recipe recipe = findById(id);
        if (recipeDTO.getChef() != null) {
            recipe.setChef(recipeDTO.getChef());
        }
        if (recipeDTO.getServings() != null) {
            recipe.setServings(recipeDTO.getServings());
        }
        if (recipeDTO.getName() != null) {
            recipe.setName(recipeDTO.getName());
        }
        if (recipeDTO.getType() != null) {
            recipe.setType(recipeDTO.getType());
        }
        if (recipeDTO.getIngredients() != null) {
            recipe.setIngredients(recipeDTO.getIngredients());
        }
        if (recipeDTO.getInstructions() != null) {
            recipe.setInstructions(recipeDTO.getInstructions());
        }
        recipeH2Repository.save(recipe);
        producer.sendRecipe(recipe, "update");
    }
}
