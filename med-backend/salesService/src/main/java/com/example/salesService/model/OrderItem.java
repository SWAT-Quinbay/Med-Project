package com.example.salesService.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderItem {
    private int productId;
    private String name;
    private String description;
    private float price;
    private float tax;
    private String imageUrl;
    private int quantity;
}
