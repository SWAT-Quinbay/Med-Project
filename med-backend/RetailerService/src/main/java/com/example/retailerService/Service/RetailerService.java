package com.example.retailerService.Service;

import com.example.retailerService.CustomException.InvalidDataProvidedException;
import com.example.retailerService.CustomException.NotEnoughQuantityException;
import com.example.retailerService.CustomException.ProductNotFoundException;
import com.example.retailerService.dto.request.OrderItem;
import com.example.retailerService.dto.request.RetailerRequest;
import com.example.retailerService.dto.response.RetailerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RetailerService {
    public RetailerResponse addRetailerProduct(RetailerRequest dealer, String token) throws InvalidDataProvidedException;
    public List<RetailerResponse> getRetailerProducts(int dealerId,String query, String token);
    void incrementQuantityViaKafka(String payload);
    boolean checkStock(int retailerId,List<OrderItem> orderItems) throws ProductNotFoundException, NotEnoughQuantityException, InvalidDataProvidedException;
    boolean reduceStock(int retailerId,List<OrderItem> orderItems) throws ProductNotFoundException, NotEnoughQuantityException, InvalidDataProvidedException;
}
