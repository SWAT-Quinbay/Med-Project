package com.example.adminService.services;

import com.example.adminService.customExceptions.InvalidDataProvidedException;
import com.example.adminService.customExceptions.NotEnoughQuanityException;
import com.example.adminService.customExceptions.ProductNotFoundException;
import com.example.adminService.dto.requests.ProductRequest;
import com.example.adminService.dto.responses.ProductDetailsResponse;
import com.example.adminService.dto.responses.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    ProductResponse getById(int productId) throws ProductNotFoundException, InvalidDataProvidedException;
    Page<ProductResponse> getAll(int page, int size) throws InvalidDataProvidedException;
    Page<ProductResponse> searchByName(String query,int page,int size) throws InvalidDataProvidedException;
    Page<ProductDetailsResponse> searchByNameAndGetDetails(String query,int page,int size) throws InvalidDataProvidedException;

    ProductResponse postProduct(ProductRequest product) throws InvalidDataProvidedException;
    Iterable<ProductResponse> postProduct(List<ProductRequest> product) throws InvalidDataProvidedException;

    boolean checkQuantity(int productId, int desiredQuantity) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException;
    boolean reduceQuantity(int productId, int desiredQuantity) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException;

    ProductResponse putProduct(ProductRequest product) throws  InvalidDataProvidedException, ProductNotFoundException;
    ProductResponse disableOrEnableProduct(int productId) throws ProductNotFoundException, InvalidDataProvidedException;

    ProductDetailsResponse getProductDetail(int productId) throws ProductNotFoundException,InvalidDataProvidedException;
    List<ProductDetailsResponse> getAllProductDetails(List<Integer> productIds) throws ProductNotFoundException,InvalidDataProvidedException;
}
