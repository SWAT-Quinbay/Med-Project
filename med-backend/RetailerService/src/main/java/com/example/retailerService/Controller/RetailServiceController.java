package com.example.retailerService.Controller;

import com.example.retailerService.CustomException.NotEnoughQuantityException;
import com.example.retailerService.CustomException.ProductNotFoundException;
import com.example.retailerService.Model.Retailer;
import com.example.retailerService.Service.RetailerService;
import com.example.retailerService.dto.request.OrderItem;
import com.example.retailerService.dto.request.RetailerRequest;
import com.example.retailerService.dto.response.RetailerResponse;
import com.example.retailerService.security.SecurityConstants;
import com.sun.deploy.net.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/retailer")
public class RetailServiceController {
    @Autowired
    RetailerService retailerService;

    @PostMapping("/add")
    public RetailerResponse addDetail(@RequestBody RetailerRequest dealer, @RequestHeader(SecurityConstants.AUTH_HEADER) String token ){
        try {
            return retailerService.addRetailerProduct(dealer,token);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/id")
    public List<RetailerResponse> getDealerById(@RequestParam int id,@RequestParam String query,@RequestHeader(SecurityConstants.AUTH_HEADER) String token ){
        try {
            return retailerService.getRetailerProducts(id,query,token);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PostMapping("/stock/check")
    public boolean checkStock(@RequestBody List<OrderItem> orderItems, HttpServletRequest request){

        try {
            int retailerId = Integer.parseInt(request.getAttribute(SecurityConstants.USER_ID).toString());
            return retailerService.checkStock(retailerId,orderItems);
        } catch (ProductNotFoundException | NotEnoughQuantityException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PostMapping("/stock/reduce")
    public boolean reduceStock(@RequestBody List<OrderItem> orderItems, HttpServletRequest request){

        try {
            int retailerId = Integer.parseInt(request.getAttribute(SecurityConstants.USER_ID).toString());
            return retailerService.reduceStock(retailerId,orderItems);
        } catch (ProductNotFoundException | NotEnoughQuantityException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
