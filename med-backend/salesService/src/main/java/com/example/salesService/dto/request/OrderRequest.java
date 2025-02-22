package com.example.salesService.dto.request;

import com.example.salesService.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private int retailerId;
    private float subTotal;
    private String paymentMethod;
    private List<OrderItem> orderItems;
}
