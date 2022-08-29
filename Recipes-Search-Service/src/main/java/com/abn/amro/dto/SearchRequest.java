package com.abn.amro.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {

    private List<FilterCondition> filterConditions = new ArrayList<>();
    private Condition freeTextCondition;
    private Integer from;
    private Integer size;

}
