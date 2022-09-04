package com.example.adminService.dto;

import com.example.adminService.dto.responses.UserResponse;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class Order {
        private int id;
        private int productId;
        private String name;
        private String description;
        private float tax;
        private float price;
        private int orderedQuantity;
        private String status;
        private UserResponse sender;
        private UserResponse receiver;
}
