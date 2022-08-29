package com.recipes.authoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recipes.authoring.dto.RecipeDTO;
import com.recipes.authoring.service.RecipeService;

@RestController
@RequestMapping(path = "v1/authoring/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<RecipeDTO> getRecipeById (@PathVariable String id) {
        RecipeDTO recipe = recipeService.getRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createNewRecipe (@RequestBody RecipeDTO recipe) {
        return new ResponseEntity<>(recipeService.createNewRecipe(recipe), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Void> updateRecipe (@PathVariable String id, @RequestBody RecipeDTO recipeDTO) {
        recipeService.updateRecipe(id, recipeDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteRecipe (@PathVariable String id) {
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
