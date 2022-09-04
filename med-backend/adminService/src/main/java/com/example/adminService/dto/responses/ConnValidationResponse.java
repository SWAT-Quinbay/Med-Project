package com.example.adminService.dto.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;



@Getter
@Builder
@ToString
public class ConnValidationResponse {

    private String status;
    private boolean isAuthenticated = false;
    private String methodType;
    private String username;
    private String userId;
    private String token;
    private String tokenKey;
    private List<GrantedAuthority> roles;
}
