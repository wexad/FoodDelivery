package com.wexad.BurgerHub.handler.exceptions;

public class UserDeletedException extends RuntimeException {
    public UserDeletedException(String message) {
        super(message);
    }
}
