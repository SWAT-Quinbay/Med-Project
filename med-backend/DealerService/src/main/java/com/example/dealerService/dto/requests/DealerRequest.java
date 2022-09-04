package com.example.dealerService.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealerRequest {
    private int productId;
    private int dealerId;
    private int stockInHand;
}
