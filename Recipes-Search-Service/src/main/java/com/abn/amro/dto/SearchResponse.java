package com.abn.amro.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private String id;
    private String name;
    private String type;
    private String chef;
    private Integer servings;
    private List<String> ingredients = new ArrayList<>();
    private List<String> instructions = new ArrayList<>();

}
