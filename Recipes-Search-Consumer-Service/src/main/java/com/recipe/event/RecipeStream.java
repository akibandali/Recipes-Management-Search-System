package com.recipe.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

import static com.recipe.event.Constants.INPUT;
public interface RecipeStream {

    @Input(INPUT)
    SubscribableChannel consumeRecipes();

}
