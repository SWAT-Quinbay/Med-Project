package com.example.salesService.dto.response;

import com.example.salesService.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long id;
    private int retailerId;
    private float subTotal;
    private String status;
    private String paymentMethod;
    private Date createdTime;
    private List<OrderItem> orderItems;
}
