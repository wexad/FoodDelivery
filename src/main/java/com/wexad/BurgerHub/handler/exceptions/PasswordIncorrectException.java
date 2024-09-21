package com.wexad.BurgerHub.handler.exceptions;

public class PasswordIncorrectException extends RuntimeException {

    public PasswordIncorrectException(String message) {
        super(message);
    }
}
