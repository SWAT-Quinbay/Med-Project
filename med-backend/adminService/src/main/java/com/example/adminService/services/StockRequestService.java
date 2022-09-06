package com.example.adminService.services;

import com.example.adminService.customExceptions.*;
import com.example.adminService.dto.requests.StockRequest;
import com.example.adminService.dto.requests.StockRequestStatusRequest;
import com.example.adminService.dto.responses.StockRequestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StockRequestService {
    List<StockRequestResponse> getOrdersForUser(int userId,String requestId,String status) throws InvalidDataProvidedException, ProductNotFoundException, UserNotFoundException;
    List<StockRequestResponse> getOrdersHistoryForUser(int userId,String requestId,String status) throws InvalidDataProvidedException, ProductNotFoundException, UserNotFoundException;
    StockRequestResponse addAdminStockRequest(StockRequest stockRequest) throws UserNotFoundException, InvalidDataProvidedException, ProductNotFoundException, NotEnoughQuanityException, ProductIsNotAvailableException;
    StockRequestResponse addDealerStockRequest(StockRequest stockRequest) throws InvalidDataProvidedException, UserNotFoundException, ProductNotFoundException, NotEnoughQuanityException, ProductIsNotAvailableException;
    StockRequestResponse updateStockRequestStatus(StockRequestStatusRequest request,String token) throws StockRequestNotFoundException, RequestAlreadyRespondedException, InvalidDataProvidedException, ProductIsNotAvailableException, NotEnoughQuanityException, ProductNotFoundException, JsonProcessingException;
}
