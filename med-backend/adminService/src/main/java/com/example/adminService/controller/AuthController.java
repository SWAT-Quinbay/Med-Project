package com.example.adminService.controller;


import com.example.adminService.dto.responses.ConnValidationResponse;
import com.example.adminService.security.SecurityConstants;
import com.example.adminService.utils.Constants;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(Constants.AUTH_BASE_URL)
public class AuthController {
    @GetMapping(value = Constants.AUTH_VALIDATE_TOKEN, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ConnValidationResponse> validateGet(HttpServletRequest request) {
        String userId = (String) request.getAttribute(SecurityConstants.USER_ID);
        String username = (String) request.getAttribute(SecurityConstants.USER_NAME);
        String token = (String) request.getAttribute(SecurityConstants.JWT_TOKEN);
        String tokenKey = (String) request.getAttribute(SecurityConstants.JWT_TOKEN_KEY);
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) request.getAttribute(SecurityConstants.AUTHORITIES_HEADER);
        return ResponseEntity.ok(
                ConnValidationResponse.builder()
                        .status(HttpStatus.OK.name())
                        .methodType(HttpMethod.GET.name())
                        .userId(userId)
                        .username(username)
                        .token(token)
                        .tokenKey(tokenKey)
                        .roles(grantedAuthorities)
                .isAuthenticated(true).build());
    }

}
