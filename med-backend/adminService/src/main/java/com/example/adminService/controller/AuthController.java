package com.example.adminService.controller;


import com.example.adminService.dto.responses.ConnValidationResponse;
import com.example.adminService.security.SecurityConstants;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping(value = "/validateToken", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ConnValidationResponse> validateGet(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String username = (String) request.getAttribute("username");
        String token = (String) request.getAttribute("jwt");
        String tokenKey = (String) request.getAttribute("jwt_key");
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) request.getAttribute(SecurityConstants.AUTHORITIES_HEADER);
        return ResponseEntity.ok(
                ConnValidationResponse.builder()
                        .status("OK")
                        .methodType(HttpMethod.GET.name())
                        .userId(userId)
                        .username(username)
                        .token(token)
                        .tokenKey(tokenKey)
                        .roles(grantedAuthorities)
                .isAuthenticated(true).build());
    }

}
