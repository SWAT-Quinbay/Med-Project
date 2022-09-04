package com.example.adminService.controller;

import com.example.adminService.customExceptions.InvalidDataProvidedException;
import com.example.adminService.customExceptions.ProductNotFoundException;
import com.example.adminService.dto.responses.ProductDetailsResponse;
import com.example.adminService.dto.requests.ProductRequest;
import com.example.adminService.dto.responses.ProductResponse;
import com.example.adminService.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class inventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/id/{id}")
    public ProductResponse getProduct(@PathVariable int id){
        try {
            return inventoryService.getById(id);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/all")
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
    @GetMapping("/search")
    public Page<ProductResponse> getAllProducts(@RequestParam String query,@RequestParam int page, @RequestParam int size){
        try {
            return inventoryService.searchByName(query,page,size);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PostMapping("/add")
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest){
        try {
            return inventoryService.postProduct(productRequest);
        }catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PostMapping("/add/all")
    public Iterable<ProductResponse> addAllProduct(@RequestBody List<ProductRequest> productRequest){
        try {
            return inventoryService.postProduct(productRequest);

        }catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PutMapping("/update")
    public ProductResponse updateProduct(@RequestBody ProductRequest productRequest){
        try {
            return inventoryService.putProduct(productRequest);

        } catch (InvalidDataProvidedException | ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PutMapping("/update/active/{id}")
    public ProductResponse disableProduct(@PathVariable int id){
        try {
            return inventoryService.disableOrEnableProduct(id);

        } catch (ProductNotFoundException | InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/detail")
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

    @GetMapping("/detail/search")
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
