package com.example.adminService.services;

import com.example.adminService.kafka.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRedisService {
    Optional<Product> getById(int productId);
    Product saveProduct(Product product);

    Iterable<Product> saveAllProduct(List<Product> products);
}
