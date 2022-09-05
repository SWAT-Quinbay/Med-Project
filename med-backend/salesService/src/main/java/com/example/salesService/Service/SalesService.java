package com.example.salesService.Service;

import com.example.salesService.CustomException.InvalidDataProvidedException;
import com.example.salesService.CustomException.NotEnoughQuantityException;
import com.example.salesService.CustomException.SalesIDNotFound;
import com.example.salesService.dto.request.OrderRequest;
import com.example.salesService.dto.response.OrderResponse;
import com.example.salesService.model.Order;

import java.util.List;

public interface SalesService {
    List<Order> searchByRetailerId(int retailerId,String query);
    OrderResponse add(OrderRequest order, String token) throws InvalidDataProvidedException, NotEnoughQuantityException;

}
