package com.example.adminService.customExceptions;

public class AdminCannotDeactivateException extends Exception{
    public AdminCannotDeactivateException(String message) {
        super(message);
    }
}
