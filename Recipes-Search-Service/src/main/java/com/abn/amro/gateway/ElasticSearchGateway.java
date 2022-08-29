package com.abn.amro.gateway;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetAliasResponse;
import com.abn.amro.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository

public class ElasticSearchGateway {

    private final ElasticsearchClient client;
    private final static String INDEX = "recipes";
    private final Logger log = LoggerFactory.getLogger(ElasticSearchGateway.class);

    public ElasticSearchGateway (ElasticsearchClient client) {
        this.client = client;
    }

    public List<Recipe> search (int from, int size, BoolQuery boolQuery) {
        List<Recipe> recipes = new ArrayList<>();
        SearchResponse<Recipe> response = null;
        try {
            response = client.search(s -> s.index(INDEX).query(q -> q.bool(boolQuery)).from(from).size(size), Recipe.class);
        }
        catch (IOException e) {
            log.error("Elastic search error occurred", e);
        }
        if (null != response) {
            List<Hit<Recipe>> hits = response.hits().hits();
            recipes.addAll(hits.stream().map(Hit::source).collect(Collectors.toList()));
        }
        return recipes;
    }



}
