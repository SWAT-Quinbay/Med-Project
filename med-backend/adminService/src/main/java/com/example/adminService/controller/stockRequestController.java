package com.example.adminService.controller;


import com.example.adminService.customExceptions.*;
import com.example.adminService.dto.requests.StockRequest;
import com.example.adminService.dto.requests.StockRequestStatusRequest;
import com.example.adminService.dto.responses.StockRequestResponse;
import com.example.adminService.models.StockRequestEntity;
import com.example.adminService.security.SecurityConstants;
import com.example.adminService.services.StockRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stock")
public class stockRequestController {

    @Autowired
    StockRequestService stockRequestService;

    @GetMapping("/receiver/{id}")
    public List<StockRequestResponse> getAllRequests(@PathVariable("id") int userId,@RequestParam String requestId,@RequestParam String status ){
        try {
            return stockRequestService.getOrdersForUser(userId,requestId,status);
        } catch (InvalidDataProvidedException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @GetMapping("/history/{id}")
    public List<StockRequestResponse> getAllOrderHistory(@PathVariable("id") int userId ,@RequestParam String requestId,@RequestParam String status ){
        try {
            return stockRequestService.getOrdersHistoryForUser(userId,requestId,status);
        } catch (InvalidDataProvidedException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @PostMapping("/admin")
    public StockRequestResponse postAdminRequests(@RequestBody StockRequest stockRequest){
        try {
            return stockRequestService.addAdminStockRequest(stockRequest);
        } catch (UserNotFoundException |InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @PostMapping("/dealer")
    public StockRequestResponse postDealerRequests(@RequestBody StockRequest stockRequest){
        try {
            return stockRequestService.addDealerStockRequest(stockRequest);
        } catch (UserNotFoundException |InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/update/status")
    public StockRequestResponse updateRequestStatus(@RequestBody StockRequestStatusRequest request, @RequestHeader(SecurityConstants.AUTH_HEADER) String token){
        try {
            return stockRequestService.updateStockRequestStatus(request,token);
        } catch (StockRequestNotFoundException | InvalidDataProvidedException
                 | NotEnoughQuanityException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
