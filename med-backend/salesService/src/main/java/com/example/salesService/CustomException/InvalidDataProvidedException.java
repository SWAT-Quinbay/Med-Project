package com.example.salesService.CustomException;

public class InvalidDataProvidedException extends Exception {
    public InvalidDataProvidedException(String message) {
        super(message);
    }
}
