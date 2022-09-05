package com.example.adminService.customExceptions;

public class ProductIsNotAvailableException extends Exception{
    public ProductIsNotAvailableException(String message) {
        super(message);
    }
}
