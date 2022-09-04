package com.example.adminService.services.implement;

import com.example.adminService.models.Product;
import com.example.adminService.repos.redis.ProductRedisRepository;
import com.example.adminService.services.ProductRedisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductRedisServiceImp implements ProductRedisService {
    @Autowired
    ProductRedisRepository productRedisRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<Product> getById(int productId) {
        return productRedisRepository.findById(productId);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRedisRepository.save(product);
    }

    @Override
    public Iterable<Product> saveAllProduct(List<Product> products) {
        return productRedisRepository.saveAll(products);
    }
}
