package com.example.adminService.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailsResponse {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private float tax;
    private float price;
    private int netQuantity;
    private boolean isAvailable;
}
