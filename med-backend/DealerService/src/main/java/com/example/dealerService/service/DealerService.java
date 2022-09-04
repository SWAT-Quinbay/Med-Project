package com.example.dealerService.service;

import com.example.dealerService.customException.InvalidDataProvidedException;
import com.example.dealerService.dto.requests.DealerRequest;
import com.example.dealerService.dto.responses.DealerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DealerService {

    public DealerResponse addDealerProduct(DealerRequest dealer,String token) throws InvalidDataProvidedException;
    public Page<DealerResponse> getDealerProducts(int dealerId,int page,int size,String token);
    List<DealerResponse> searchAllDealers(String query, int page, int size, String token);
    void incrementQuantityViaKafka(String payload);

    boolean checkProductStock(int dealerId,int productId,int desiredQuantity);
    boolean reduceProductStock(int dealerId,int productId,int quantity);
}
