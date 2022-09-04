package com.example.adminService.customExceptions;

public class NotEnoughQuanityException extends Exception {
    public NotEnoughQuanityException(String message) {
        super(message);
    }
}
