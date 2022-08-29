package com.abn.amro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abn.amro.dto.SearchRequest;
import com.abn.amro.dto.SearchResponse;
import com.abn.amro.exception.BadRequestException;
import com.abn.amro.service.RecipeSearchService;

@RestController
@RequestMapping(path = "v1/search")
public class RecipeSearchController {

    private final RecipeSearchService service;

    public RecipeSearchController (RecipeSearchService service) {
        this.service = service;
    }

    @PostMapping(path = "/recipes", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<SearchResponse>> searchRecipes (@RequestBody SearchRequest searchRequest) {
        validateSearchRequest(searchRequest);
        List<SearchResponse> searchResponse = service.searchRecipes(searchRequest);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }

    private void validateSearchRequest (SearchRequest searchRequest) {
        if (!CollectionUtils.isEmpty(searchRequest.getFilterConditions()) || null != searchRequest.getFreeTextCondition()) {
            return;
        }
        throw new BadRequestException("Add at least one search condition");
    }

}
