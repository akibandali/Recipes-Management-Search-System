package com.abn.amro.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.abn.amro.dto.*;
import com.abn.amro.exception.BadRequestException;
import com.abn.amro.gateway.ElasticSearchGateway;
import com.abn.amro.model.Recipe;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeSearchService {
    private final ElasticSearchGateway elasticSearchGateway;
    private static Integer DEFAULT_SIZE = 100;

    public RecipeSearchService (ElasticSearchGateway elasticSearchGateway) {
        this.elasticSearchGateway = elasticSearchGateway;
    }

    public List<SearchResponse> searchRecipes (SearchRequest searchRequest) {
        BoolQuery boolQuery = buildBoolQuery(searchRequest);
        int from = searchRequest.getFrom() != null ? searchRequest.getFrom() : 0;
        int size = searchRequest.getSize() != null ? searchRequest.getSize() : DEFAULT_SIZE;
        List<Recipe> recipes = elasticSearchGateway.search(from, size, boolQuery);
        return map(recipes);
    }

    private List<SearchResponse> map (List<Recipe> recipes) {
        List<SearchResponse> searchResponses = new ArrayList<>();
        recipes.forEach(recipe -> {
            SearchResponse searchResponse = SearchResponse.builder()
                                                          .id(recipe.getId())
                                                          .name(recipe.getName())
                                                          .type(recipe.getType())
                                                          .chef(recipe.getChef())
                                                          .servings(recipe.getServings())
                                                          .ingredients(recipe.getIngredients())
                                                          .instructions(recipe.getInstructions())
                                                          .build();

            searchResponses.add(searchResponse);
        });
        return searchResponses;
    }

    private BoolQuery buildBoolQuery (SearchRequest searchRequest) {

        List<Query> includeFilterConditions = getQueries(ConditionType.INCLUDE, searchRequest.getFilterConditions());
        List<Query> excludeFilterConditions = getQueries(ConditionType.EXCLUDE, searchRequest.getFilterConditions());
        Query freeTextQuery = buildFreeTextQuery(searchRequest.getFreeTextCondition());
        List<Query> must = new ArrayList<>(includeFilterConditions);
        if (freeTextQuery != null) {
            must.add(freeTextQuery);
        }
        List<Query> mustNot = new ArrayList<>(excludeFilterConditions);
        return BoolQuery.of(bool -> {
            bool.must(must);
            bool.mustNot(mustNot);
            return bool;
        });

    }

    private List<Query> getQueries (ConditionType type, List<FilterCondition> conditions) {
        if(CollectionUtils.isEmpty(conditions)){
            return new ArrayList<>();
        }

        return conditions.stream()
                         .filter(condition -> type.value().equals(condition.getType().value()))
                         .map(this::buildQuery)
                         .collect(Collectors.toList());

    }

    private Query buildQuery (Condition condition) {

        if (null == condition.getField() || null == condition.getValue()) {
            throw new BadRequestException("field amd value both should be populated");

        }
        return MatchQuery.of(param -> param.field(condition.getField()).query(condition.getValue()))._toQuery();
    }

    private Query buildFreeTextQuery (Condition condition) {

        if (condition!=null && null != condition.getField() && null != condition.getValue()) {
            return MatchQuery.of(param -> param.field(condition.getField()).query(condition.getValue()).fuzzyTranspositions(true))
                             ._toQuery();
        }
        return null;
    }

}
