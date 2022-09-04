package com.example.retailerService.Repository;

import com.example.retailerService.Model.Retailer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public interface RetailerRepository extends PagingAndSortingRepository<Retailer,Integer> {
    List<Retailer> findByRetailerId(int retailerId, Sort sort);
    Optional<Retailer> findByRetailerIdAndProductId(int retailerId, int productId);
}