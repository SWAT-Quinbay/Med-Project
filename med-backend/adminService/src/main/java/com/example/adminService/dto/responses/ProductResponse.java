package com.example.adminService.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


// for admin data retrieval
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private float tax;
    private float price;
    private int netQuantity;
    private boolean isAvailable;
    private Date createdAt;
}
