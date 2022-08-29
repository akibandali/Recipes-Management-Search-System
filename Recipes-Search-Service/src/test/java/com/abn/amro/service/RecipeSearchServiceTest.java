package com.abn.amro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.abn.amro.dto.ConditionType;
import com.abn.amro.dto.FilterCondition;
import com.abn.amro.dto.SearchRequest;
import com.abn.amro.dto.SearchResponse;
import com.abn.amro.exception.BadRequestException;
import com.abn.amro.gateway.ElasticSearchGateway;
import com.abn.amro.model.Recipe;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;

@RunWith(MockitoJUnitRunner.class)
public class RecipeSearchServiceTest {

    @Mock
    private ElasticSearchGateway elasticSearchGateway;
    @InjectMocks
    private RecipeSearchService recipeSearchService;

    @Test
    public void testSearch () {
        Recipe recipe = Recipe.builder().id(UUID.randomUUID().toString()).type("Veg").build();
        doReturn(Collections.singletonList(recipe)).when(elasticSearchGateway).search(anyInt(), anyInt(), any(BoolQuery.class));
        FilterCondition filterCondition = FilterCondition.builder().type(ConditionType.INCLUDE).build();
        filterCondition.setField("type");
        filterCondition.setValue("Veg");
        SearchRequest searchRequest = SearchRequest.builder().filterConditions(Collections.singletonList(filterCondition)).build();
        List<SearchResponse> actual = recipeSearchService.searchRecipes(searchRequest);
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getType()).isEqualTo("Veg");
    }

    @Test(expected = BadRequestException.class)
    public void testSearchWithBadRequest () {
        Recipe recipe = Recipe.builder().id(UUID.randomUUID().toString()).type("Veg").build();
        doReturn(Collections.singletonList(recipe)).when(elasticSearchGateway).search(anyInt(), anyInt(), any(BoolQuery.class));
        FilterCondition filterCondition = FilterCondition.builder().type(ConditionType.INCLUDE).build();
        filterCondition.setValue("Veg");
        SearchRequest searchRequest = SearchRequest.builder().filterConditions(Collections.singletonList(filterCondition)).build();
        recipeSearchService.searchRecipes(searchRequest);
    }
}
