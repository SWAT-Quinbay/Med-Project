package com.example.adminService.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {
    private int productId;
    private int requestedQuantity;
    private int senderId;
    private int receiverId;
}
