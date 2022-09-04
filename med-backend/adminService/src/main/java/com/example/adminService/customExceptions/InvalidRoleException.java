package com.example.adminService.customExceptions;

public class InvalidRoleException extends Exception{
    public InvalidRoleException(String message) {
        super(message);
    }
}
