package com.recipes.authoring.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2089454849611061079L;

    public NotFoundException(String id) {
        super(String.format("Recipe with id: %s not found", id));

    }
}
