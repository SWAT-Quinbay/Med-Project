package com.example.retailerService.Controller;

import com.example.retailerService.CustomException.InvalidDataProvidedException;
import com.example.retailerService.CustomException.NotEnoughQuantityException;
import com.example.retailerService.CustomException.ProductNotFoundException;
import com.example.retailerService.Service.RetailerService;
import com.example.retailerService.dto.request.OrderItem;
import com.example.retailerService.dto.request.RetailerRequest;
import com.example.retailerService.dto.response.RetailerResponse;
import com.example.retailerService.security.SecurityConstants;
import com.example.retailerService.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(Constants.RETAILER_BASE_URL)
public class RetailServiceController {
    @Autowired
    RetailerService retailerService;

    @PostMapping(Constants.RETAILER_ADD)
    public RetailerResponse addDetail(@RequestBody RetailerRequest dealer, @RequestHeader(SecurityConstants.AUTH_HEADER) String token) {
        try {
            return retailerService.addRetailerProduct(dealer, token);
        }catch (InvalidDataProvidedException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @GetMapping(Constants.RETAILER_ID)
    public List<RetailerResponse> getDealerById(@RequestParam int id, @RequestParam String query, @RequestHeader(SecurityConstants.AUTH_HEADER) String token) {
        try {
            return retailerService.getRetailerProducts(id, query, token);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PostMapping(Constants.RETAILER_STOCK_CHECK)
    public boolean checkStock(@RequestBody List<OrderItem> orderItems, HttpServletRequest request) {

        try {
            int retailerId = Integer.parseInt(request.getAttribute(SecurityConstants.USER_ID).toString());
            return retailerService.checkStock(retailerId, orderItems);
        } catch (ProductNotFoundException | NotEnoughQuantityException | InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PostMapping(Constants.RETAILER_STOCK_REDUCE)
    public boolean reduceStock(@RequestBody List<OrderItem> orderItems, HttpServletRequest request) {

        try {
            int retailerId = Integer.parseInt(request.getAttribute(SecurityConstants.USER_ID).toString());
            return retailerService.reduceStock(retailerId, orderItems);
        } catch (ProductNotFoundException | NotEnoughQuantityException | InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }
}
