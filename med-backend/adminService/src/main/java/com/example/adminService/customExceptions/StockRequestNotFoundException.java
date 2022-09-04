package com.example.adminService.customExceptions;

public class StockRequestNotFoundException extends Exception {
    public StockRequestNotFoundException(String message) {
        super(message);
    }
}
