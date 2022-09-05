package com.example.adminService.controller;

import com.example.adminService.customExceptions.InvalidDataProvidedException;
import com.example.adminService.customExceptions.ProductNotFoundException;
import com.example.adminService.dto.responses.ProductDetailsResponse;
import com.example.adminService.dto.requests.ProductRequest;
import com.example.adminService.dto.responses.ProductResponse;
import com.example.adminService.services.InventoryService;
import com.example.adminService.utils.Constants;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(Constants.INVENTORY_BASE_URL)
public class inventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping(Constants.INVENTORY_GET_BY_ID)
    public ProductResponse getProduct(@PathVariable int id){
        try {
            return inventoryService.getById(id);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping(Constants.INVENTORY_GET_ALL)
    public Page<ProductResponse> getAllProducts(@RequestParam int page, @RequestParam int size){
        try {
            return inventoryService.getAll(page,size);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    @GetMapping(Constants.INVENTORY_SEARCH)
    public Page<ProductResponse> getAllProducts(@RequestParam String query,@RequestParam int page, @RequestParam int size){
        try {
            return inventoryService.searchByName(query,page,size);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PostMapping(Constants.INVENTORY_ADD)
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest){
        try {
            return inventoryService.postProduct(productRequest);
        }catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PostMapping(Constants.INVENTORY_ADD_ALL)
    public Iterable<ProductResponse> addAllProduct(@RequestBody List<ProductRequest> productRequest){
        try {
            return inventoryService.postProduct(productRequest);

        }catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PutMapping(Constants.INVENTORY_UPDATE)
    public ProductResponse updateProduct(@RequestBody ProductRequest productRequest){
        try {
            return inventoryService.putProduct(productRequest);

        } catch (InvalidDataProvidedException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PutMapping(Constants.INVENTORY_UPDATE_ACTIVE)
    public ProductResponse disableProduct(@PathVariable int id){
        try {
            return inventoryService.disableOrEnableProduct(id);

        } catch (ProductNotFoundException | InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping(Constants.INVENTORY_DETAIL)
    public ProductDetailsResponse getProductDetails(@RequestParam int id){
        try {
            return inventoryService.getProductDetail(id);

        } catch (ProductNotFoundException |InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
//    @GetMapping("/detail/all")
//    public List<ProductDetailsResponse> getProductDetails(@RequestBody List<Integer> ids){
//        try {
//            return inventoryService.getAllProductDetails(ids);
//
//        } catch (ProductNotFoundException | InvalidDataProvidedException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
//        }catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
//        }
//    }

    @GetMapping(Constants.INVENTORY_DETAIL_SEARCH)
    public Page<ProductDetailsResponse> searchProducts(@RequestParam String query,@RequestParam int page, @RequestParam int size){
        try {
            return inventoryService.searchByNameAndGetDetails(query,page,size);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
