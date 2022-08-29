package com.recipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.recipe.gateway.ElasticSearchGateway;

@Component
public class IndexCreationAfterStartup {
    private final Logger log = LoggerFactory.getLogger(IndexCreationAfterStartup.class);
    private final ElasticSearchGateway elasticSearchGateway;

    public IndexCreationAfterStartup (ElasticSearchGateway elasticSearchGateway) {
        this.elasticSearchGateway = elasticSearchGateway;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerIndexCreation () {
        log.info("Triggered Index Creation Event");
        elasticSearchGateway.createIndex();
    }
}
