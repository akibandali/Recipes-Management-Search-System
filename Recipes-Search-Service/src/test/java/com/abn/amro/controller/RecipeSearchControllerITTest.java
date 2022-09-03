package com.abn.amro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.abn.amro.dto.Condition;
import com.abn.amro.dto.SearchRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeSearchControllerITTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ElasticsearchClient elasticsearchClient;

    @Test
   public void search () throws Exception {
        SearchRequest searchRequest = SearchRequest.builder().freeTextCondition(new Condition("type", "Veg")).build();
        mockMvc.perform(post("/v1/search/recipes").contentType("application/json").content(objectMapper.writeValueAsString(searchRequest)))
               .andExpect(status().isOk());
    }
}
