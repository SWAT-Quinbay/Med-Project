package com.example.adminService.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockUpdateResponse {
    int senderId;
    int productId;
    int quantity;
}
