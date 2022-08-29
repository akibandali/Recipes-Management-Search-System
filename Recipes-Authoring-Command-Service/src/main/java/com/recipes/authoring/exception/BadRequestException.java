package com.recipes.authoring.exception;


public class BadRequestException extends RuntimeException{
    private static final long serialVersionUID = 9079454849611061074L;

   public BadRequestException(String message){
        super(message);
    }
}
