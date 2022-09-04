package com.example.adminService.dto.requests;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequest {
    private int id;
    private String name;
    private String password;
}
