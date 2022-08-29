package com.recipes.authoring.repo;

import com.recipes.authoring.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeH2Repository extends JpaRepository<Recipe, String> {

}
