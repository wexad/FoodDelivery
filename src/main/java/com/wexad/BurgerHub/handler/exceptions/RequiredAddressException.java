package com.wexad.BurgerHub.handler.exceptions;

public class RequiredAddressException extends RuntimeException {
    public RequiredAddressException(String message) {
        super(message);
    }
}
