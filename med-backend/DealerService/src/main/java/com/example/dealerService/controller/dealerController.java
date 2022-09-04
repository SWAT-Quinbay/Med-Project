package com.example.dealerService.controller;

import com.example.dealerService.dto.requests.DealerRequest;
import com.example.dealerService.dto.responses.DealerResponse;
import com.example.dealerService.security.SecurityConstants;
import com.example.dealerService.service.DealerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/dealer")
@Slf4j
public class dealerController {
    @Autowired
    DealerService dealerService;

    @PostMapping("/add")
    public DealerResponse addDetail(@RequestBody DealerRequest dealer, @RequestHeader(SecurityConstants.AUTH_HEADER) String token ){
        try {
            return dealerService.addDealerProduct(dealer,token);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    @GetMapping("/search/all")
    public List<DealerResponse> searchAll(@RequestParam String query, @RequestParam int page, @RequestParam int size, @RequestHeader(SecurityConstants.AUTH_HEADER) String token ){
        try {
            return dealerService.searchAllDealers(query,page,size,token);
        }catch (Exception e){
            e.printStackTrace();
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    @GetMapping("/id")
    public Page<DealerResponse> getDealerById(@RequestParam int id,@RequestParam int page,@RequestParam int size,@RequestHeader(SecurityConstants.AUTH_HEADER) String token ){
        try {
            return dealerService.getDealerProducts(id,page,size,token);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @GetMapping("/check")
    public boolean getDealerById(@RequestParam int dealerId,@RequestParam int productId,@RequestParam int quantity){
        try {
            return dealerService.checkProductStock(dealerId,productId,quantity);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    @GetMapping("/reduce")
    public boolean reduceDealerQuantity(@RequestParam int dealerId,@RequestParam int productId,@RequestParam int quantity){
        try {
            return dealerService.reduceProductStock(dealerId,productId,quantity);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
