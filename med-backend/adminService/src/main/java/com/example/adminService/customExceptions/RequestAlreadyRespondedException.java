package com.example.adminService.customExceptions;

public class RequestAlreadyRespondedException extends Exception {
    public RequestAlreadyRespondedException(String message) {
        super(message);
    }
}
