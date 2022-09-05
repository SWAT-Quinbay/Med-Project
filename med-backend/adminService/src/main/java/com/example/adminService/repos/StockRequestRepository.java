package com.example.adminService.repos;

import com.example.adminService.kafka.models.StockRequestEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StockRequestRepository extends PagingAndSortingRepository<StockRequestEntity,Integer> {
    List<StockRequestEntity> findAllBySenderId(int senderId, Sort sort);
    List<StockRequestEntity> findAllByReceiverId(int receiverId, Sort sort);
}
