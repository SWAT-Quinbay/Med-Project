package com.example.dealerService.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealerResponse {
    private int id;
    private int dealerId;
    private int productId;
    private ProductDetailsResponse productDetail;
    private int stockInHand;
}
