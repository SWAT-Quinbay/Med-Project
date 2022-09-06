package com.example.adminService.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
public class ConnClientValidationResponse {
    private boolean isAuthenticated = false;
    private String methodType;
    private String username;
    private String userId;
    private String status;
    private List<GrantedAuthority> roles;
}
