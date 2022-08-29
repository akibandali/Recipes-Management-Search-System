package com.abn.amro.dto;

public enum ConditionType {
    INCLUDE("include"), EXCLUDE("exclude");

    private String value;

    ConditionType(String value) {
        this.value = value;
    }

    public String value () {
        return value;
    }
}
