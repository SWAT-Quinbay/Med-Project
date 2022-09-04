package com.example.salesService.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCheckRequest {
    private int productId;
    private int quantity;
}
