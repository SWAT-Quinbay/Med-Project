package com.example.retailerService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailerResponse {
    private int id;
    private int retailerId;
    private int productId;
    private ProductDetailsResponse productDetail;
    private int stockInHand;
}
