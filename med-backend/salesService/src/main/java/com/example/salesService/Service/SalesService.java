package com.example.salesService.Service;

import com.example.salesService.CustomException.InvalidDataProvidedException;
import com.example.salesService.CustomException.NotEnoughQuantityException;
import com.example.salesService.CustomException.SalesIDNotFound;
import com.example.salesService.model.Order;

import java.util.List;

public interface SalesService {
    List<Order> searchByRetailerId(int retailerId,String query);
    Order add(Order order,String token) throws InvalidDataProvidedException, NotEnoughQuantityException;

}
