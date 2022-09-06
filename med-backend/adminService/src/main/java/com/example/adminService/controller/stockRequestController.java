package com.example.adminService.controller;


import com.example.adminService.customExceptions.*;
import com.example.adminService.dto.requests.StockRequest;
import com.example.adminService.dto.requests.StockRequestStatusRequest;
import com.example.adminService.dto.responses.StockRequestResponse;
import com.example.adminService.security.SecurityConstants;
import com.example.adminService.services.StockRequestService;
import com.example.adminService.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(Constants.STOCK_BASE_URL)
public class stockRequestController {

    @Autowired
    StockRequestService stockRequestService;

    @GetMapping(Constants.STOCK_GET_BY_RECEIVER)
    public List<StockRequestResponse> getAllRequests(@PathVariable("id") int userId, @RequestParam String requestId, @RequestParam String status) {
        try {
            return stockRequestService.getOrdersForUser(userId, requestId, status);
        } catch (InvalidDataProvidedException | ProductNotFoundException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @GetMapping(Constants.STOCK_GET_BY_SENDER)
    public List<StockRequestResponse> getAllOrderHistory(@PathVariable("id") int userId, @RequestParam String requestId, @RequestParam String status) {
        try {
            return stockRequestService.getOrdersHistoryForUser(userId, requestId, status);
        } catch (InvalidDataProvidedException | ProductNotFoundException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PostMapping(Constants.STOCK_REQUEST_FOR_ADMIN)
    public StockRequestResponse postAdminRequests(@RequestBody StockRequest stockRequest) {
        try {
            return stockRequestService.addAdminStockRequest(stockRequest);
        } catch (UserNotFoundException | InvalidDataProvidedException | ProductIsNotAvailableException |
                 NotEnoughQuanityException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PostMapping(Constants.STOCK_REQUEST_FOR_DEALER)
    public StockRequestResponse postDealerRequests(@RequestBody StockRequest stockRequest) {
        try {
            return stockRequestService.addDealerStockRequest(stockRequest);
        } catch (UserNotFoundException | InvalidDataProvidedException | ProductIsNotAvailableException |
                 ProductNotFoundException | NotEnoughQuanityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PutMapping(Constants.STOCK_UPDATE_STATUS)
    public StockRequestResponse updateRequestStatus(@RequestBody StockRequestStatusRequest request, @RequestHeader(SecurityConstants.AUTH_HEADER) String token) {
        try {
            return stockRequestService.updateStockRequestStatus(request, token);
        } catch (StockRequestNotFoundException | RequestAlreadyRespondedException | InvalidDataProvidedException |
                 NotEnoughQuanityException |
                 ProductNotFoundException | ProductIsNotAvailableException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }
}
