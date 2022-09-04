package com.example.retailerService.Model;

import lombok.Data;

import java.util.List;

@Data
public class Validation {
    private boolean response;
    private String userId;
    private List<Retailer>products;
    private String role;
}
