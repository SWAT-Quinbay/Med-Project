package com.example.adminService.customExceptions;

public class PostgresException extends Exception {
    public PostgresException(String message) {
        super(message);
    }
}
