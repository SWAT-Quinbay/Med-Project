package com.example.salesService.Controller;

import com.example.salesService.CustomException.InvalidDataProvidedException;
import com.example.salesService.CustomException.NotEnoughQuantityException;
import com.example.salesService.CustomException.SalesIDNotFound;
import com.example.salesService.Service.SalesService;
import com.example.salesService.dto.request.OrderRequest;
import com.example.salesService.model.Order;
import com.example.salesService.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class SalesController {
    @Autowired
    SalesService salesService;

    @GetMapping("/user")
    public List<Order> searchOrderForUser(@RequestParam int id,@RequestParam String query){
        return salesService.searchByRetailerId(id,query);
    }

    @PostMapping("/make")
    public Order searchOrderForUser(@RequestBody Order order, @RequestHeader(SecurityConstants.AUTH_HEADER) String token){
        try {
            return salesService.add(order,token);
        } catch (InvalidDataProvidedException | NotEnoughQuantityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
