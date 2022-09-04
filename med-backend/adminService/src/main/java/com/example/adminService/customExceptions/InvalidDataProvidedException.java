package com.example.adminService.customExceptions;

public class InvalidDataProvidedException extends Exception {
    public InvalidDataProvidedException(String message) {
        super(message);
    }
}
