package com.example.dealerService.repos;

import com.example.dealerService.model.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DealerRepository extends PagingAndSortingRepository<Dealer,Integer> {
//    List<Dealer> findByProductName(String productName);
    Page<Dealer> findByDealerId(int dealerId, Pageable pageable);
    List<Dealer> findAll(Sort sort);
    Optional<Dealer> findByDealerIdAndProductId(int dealerId,int productId);
}
