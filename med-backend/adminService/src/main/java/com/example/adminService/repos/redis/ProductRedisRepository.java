package com.example.adminService.repos.redis;


import com.example.adminService.kafka.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRedisRepository extends CrudRepository<Product, Integer> {
}
