package com.example.adminService.repos;

import com.example.adminService.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InventoryRepository extends PagingAndSortingRepository<Product, Integer> {
    Page<Product> findAllByNameContainingIgnoreCase(String query, Pageable pageable);
    Page<Product> findAllByNameContainingIgnoreCaseAndIsAvailableTrue(String query, Pageable pageable);

}
