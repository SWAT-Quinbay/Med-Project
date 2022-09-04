package com.example.dealerService.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
}