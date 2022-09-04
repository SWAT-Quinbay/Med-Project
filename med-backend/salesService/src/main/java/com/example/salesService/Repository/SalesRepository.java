package com.example.salesService.Repository;


import com.example.salesService.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends MongoRepository<Order,Integer> {

}
