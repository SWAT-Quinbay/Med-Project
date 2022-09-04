package com.example.salesService.dto.request;

import com.example.salesService.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private int retailerId;
    private float subTotal;
    private String status;
    private String paymentMethod;
    private List<OrderItem> orderItems;
}
