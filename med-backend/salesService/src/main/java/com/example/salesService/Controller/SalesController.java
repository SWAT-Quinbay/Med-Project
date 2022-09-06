package com.example.salesService.Controller;

import com.example.salesService.CustomException.InvalidDataProvidedException;
import com.example.salesService.CustomException.NotEnoughQuantityException;
import com.example.salesService.Service.SalesService;
import com.example.salesService.dto.request.OrderRequest;
import com.example.salesService.dto.response.OrderResponse;
import com.example.salesService.model.Order;
import com.example.salesService.security.SecurityConstants;
import com.example.salesService.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(Constants.ORDER_BASE_URL)
public class SalesController {
    @Autowired
    SalesService salesService;

    @GetMapping(Constants.ORDER_GET_BY_RETAILER)
    public List<Order> searchOrderForUser(@RequestParam int id, @RequestParam String query) {
        try {
            return salesService.searchByRetailerId(id, query);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PostMapping(Constants.ORDER_MAKE)
    public OrderResponse searchOrderForUser(@RequestBody OrderRequest order, @RequestHeader(SecurityConstants.AUTH_HEADER) String token) {
        try {
            return salesService.add(order, token);
        } catch (InvalidDataProvidedException | NotEnoughQuantityException e) {
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
