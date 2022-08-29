package com.recipes.event;

import com.recipes.authoring.model.Recipe;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeProducer {
    private final RecipeStream recipeStream;

    public RecipeProducer(RecipeStream loansStreams) {
        this.recipeStream = loansStreams;
    }

    public void sendRecipe(final Recipe recipe,String operation) {
        log.info("Sending recipe {} ", recipe);
        MessageChannel messageChannel = recipeStream.publishRecipe();
        messageChannel.send(MessageBuilder.withPayload(recipe)
                                          .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                                          .setHeader("operation", operation)
                                          .build());
    }
}
