package com.recipe.event;

import com.recipe.entity.Recipe;
import com.recipe.gateway.ElasticSearchGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.recipe.event.Constants.INPUT;

@Component
@Slf4j
public class RecipeConsumer {

    private final RecipeStream RecipeStream;
    private final ElasticSearchGateway elasticSearchGateway;

    public RecipeConsumer (RecipeStream loansStreams, ElasticSearchGateway gateway) {
        this.RecipeStream = loansStreams;
        this.elasticSearchGateway = gateway;
    }

    @StreamListener(value = INPUT)
    public void handleEvent (Message<Recipe> recipeMessage) throws Exception {
        log.info("Consuming Recipe {}", recipeMessage);
        String operation = (String) recipeMessage.getHeaders().get("operation");
        if (operation == null) {
            return;
        }
        if (operation.equals("create") || operation.equals("update")) {
            elasticSearchGateway.indexDocument(recipeMessage.getPayload());
        }
        else if (operation.equals("delete")) {
            elasticSearchGateway.deleteDocument(recipeMessage.getPayload().getId());
        }
    }

}
