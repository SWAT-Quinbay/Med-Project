package com.example.retailerService.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetailerRequest {
    private int productId;
    private int retailerId;
    private int stockInHand;
}
