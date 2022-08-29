package com.recipes.event;


 import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RecipeStream {

    String OUTPUT = "recipe-produce";

    @Output(OUTPUT)
    MessageChannel publishRecipe();
}
