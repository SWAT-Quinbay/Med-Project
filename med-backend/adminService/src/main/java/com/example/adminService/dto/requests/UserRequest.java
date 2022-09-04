package com.example.adminService.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private int id;
    private String name;
    private String username;
    private String password;
    private Date createdAt;
    private String role;
}
