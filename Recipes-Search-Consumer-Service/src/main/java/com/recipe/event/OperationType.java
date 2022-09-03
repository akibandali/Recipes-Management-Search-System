package com.recipe.event;

public enum OperationType {
    CREATE("create"), UPDATE("update"),DELETE("delete");

    private String value;

    OperationType(String value) {
        this.value = value;
    }

    public String value () {
        return value;
    }
}
