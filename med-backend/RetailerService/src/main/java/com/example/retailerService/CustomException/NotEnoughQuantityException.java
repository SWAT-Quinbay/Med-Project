package com.example.retailerService.CustomException;

public class NotEnoughQuantityException extends Exception {
    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
