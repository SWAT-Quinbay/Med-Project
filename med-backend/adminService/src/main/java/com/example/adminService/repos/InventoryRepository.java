package com.example.adminService.repos;

import com.example.adminService.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface InventoryRepository extends PagingAndSortingRepository<Product, Integer> {
    Page<Product> findAllByNameContainingIgnoreCase(String query, Pageable pageable);
    Optional<Product> findByName(String name);
    Page<Product> findAllByNameContainingIgnoreCaseAndIsAvailableTrue(String query, Pageable pageable);

}
