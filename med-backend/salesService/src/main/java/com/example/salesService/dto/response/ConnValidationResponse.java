package com.example.salesService.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnValidationResponse {
    private String status;
    private boolean isAuthenticated = false;
    private String methodType;
    private String username;
    private String userId;
    private String token;
}
