package com.recipe.gateway;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import co.elastic.clients.elasticsearch.core.DeleteRequest;
import com.recipe.entity.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetAliasResponse;

@Repository

public class ElasticSearchGateway {

    private final ElasticsearchClient client;
    private final static String INDEX = "recipes";
    private final Logger log = LoggerFactory.getLogger(ElasticSearchGateway.class);

    public ElasticSearchGateway (ElasticsearchClient client) {
        this.client = client;
    }


    public void indexDocument (Recipe recipe) {
        try {
            client.index(doc -> doc.index(INDEX).id(recipe.getId()).document(recipe));
            log.info("Indexed doc- {}", recipe);
        }
        catch (IOException e) {
            log.error("error occurred while Indexing doc", e);
        }

    }

    public void deleteDocument(String id) {

            DeleteRequest deleteRequest = DeleteRequest.of(index -> index.index(INDEX).id(id));
            try {
                 client.delete(deleteRequest);
                log.info("Deleted doc- {}", id);
            }
            catch (IOException e) {
                log.error("error occurred while deleting doc", e);
            }
    }

    public void createIndex () {
        FileReader fileReader = null;
        try {
            File mappingFile = new ClassPathResource("recipes-index-mapping.json").getFile();
            fileReader = new FileReader(mappingFile);
        }
        catch (IOException e) {
            log.error("error occurred while reading index mapping", e);
        }
        FileReader finalFile = fileReader;
        CreateIndexRequest createIndexRequest = CreateIndexRequest.of(b -> b.index(INDEX).withJson(finalFile));
        try {
            GetAliasResponse alias = client.indices().getAlias();
            if (alias.result().containsKey(INDEX)) {
                log.info("Index already exists");
            }
            else {
                boolean created = client.indices().create(createIndexRequest).acknowledged();
                if (created) {
                    log.info("Index created");
                } } }
        catch (Exception e) {
            log.error("error occurred while creating index", e);
        }
        finally {
            try {
                fileReader.close();
            }
            catch (IOException e) {
                log.error("error occurred while closing file reader", e);
            }
        }
    }
}
