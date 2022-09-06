package com.example.adminService.customExceptions;

public class ProductAlreadyExistException extends Exception{
    public ProductAlreadyExistException(String message) {
        super(message);
    }
}
