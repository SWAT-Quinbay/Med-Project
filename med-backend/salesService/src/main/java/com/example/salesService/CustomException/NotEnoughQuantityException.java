package com.example.salesService.CustomException;

public class NotEnoughQuantityException extends Exception {
    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
