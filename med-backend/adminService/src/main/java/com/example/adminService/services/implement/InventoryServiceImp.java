package com.example.adminService.services.implement;

import com.example.adminService.customExceptions.*;
import com.example.adminService.dto.requests.ProductRequest;
import com.example.adminService.dto.responses.ProductDetailsResponse;
import com.example.adminService.dto.responses.ProductResponse;
import com.example.adminService.models.Product;
import com.example.adminService.repos.InventoryRepository;
import com.example.adminService.services.InventoryService;
import com.example.adminService.services.ProductRedisService;
import com.example.adminService.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class InventoryServiceImp implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ProductRedisService productRedisService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductResponse getById(int productId) throws ProductNotFoundException, InvalidDataProvidedException {

        Product product = getProductObjectById(productId);

        return  mapProductResponse(product);
    }

    private Product getProductObjectById(int productId) throws ProductNotFoundException, InvalidDataProvidedException {
        if (!Utilities.validNumber(productId)) {
            throw new InvalidDataProvidedException("Numbers should be positive");
        }
        Optional<Product> productRedisEntity = productRedisService.getById(productId);

        if(productRedisEntity.isPresent()){
            log.info("Retrieved from Redis "+productRedisEntity.get());
            return productRedisEntity.get();
        }

        Optional<Product> product = inventoryRepository.findById(productId);

        if (!product.isPresent()) {
            throw new ProductNotFoundException("Product Not Found");
        }
        productRedisService.saveProduct(product.get());

        return product.get();
    }

    @Override
    public Page<ProductResponse> getAll(int page, int size) throws InvalidDataProvidedException {

        if (!(Utilities.validPage(page) && Utilities.validNumber(size))) {
            throw new InvalidDataProvidedException("Numbers Should be Positive");
        }

        PageRequest pr = PageRequest.of(page, size, Sort.by("netQuantity").descending());

        return mapAllProductResponse(inventoryRepository.findAll(pr));
    }


    @Override
    public Page<ProductResponse> searchByName(String query, int page, int size) throws InvalidDataProvidedException {

        query = Utilities.formatString(query);

        if (!(Utilities.validPage(page) && Utilities.validNumber(size))) {
            throw new InvalidDataProvidedException("Numbers Should be Positive");
        }

        PageRequest pr = PageRequest.of(page, size,
                Sort.by("name").ascending().and(Sort.by("netQuantity").descending()));

        Page<Product> results = inventoryRepository.findAllByNameContainingIgnoreCase(query, pr);
        return mapAllProductResponse(results);
    }

    @Override
    public Page<ProductDetailsResponse> searchByNameAndGetDetails(String query, int page, int size) throws InvalidDataProvidedException {
        query = Utilities.formatString(query);

        if (!(Utilities.validPage(page) && Utilities.validNumber(size))) {
            throw new InvalidDataProvidedException("Numbers Should be Positive");
        }

        PageRequest pr = PageRequest.of(page, size,
                Sort.by("name").ascending().and(Sort.by("netQuantity").descending()));

        Page<Product> results = inventoryRepository.findAllByNameContainingIgnoreCaseAndIsAvailableTrue(query, pr);
        return mapAllProductDetailResponse(results);
    }

    @Override
    public ProductResponse postProduct(ProductRequest productRequest) throws InvalidDataProvidedException, ProductAlreadyExistException {
        formatAndValidateProductDetails(productRequest);
        Product product = getProductFromRequest(productRequest);

        Optional<Product> havProduct = inventoryRepository.findByName(product.getName());
        if(havProduct.isPresent()){
            throw new ProductAlreadyExistException("Product name should be unique");
        }

        productRedisService.saveProduct(product);
        return mapProductResponse(inventoryRepository.save(product));
    }

    @Override
    public Iterable<ProductResponse> postProduct(List<ProductRequest> productRequests)
            throws InvalidDataProvidedException {

        formatAndValidateProductDetails(productRequests);

        List<Product> products = getAllProductFromRequest(productRequests);
        productRedisService.saveAllProduct(products);

        return mapAllSavedProductResponse(inventoryRepository.saveAll(products));
    }

    @Override
    public ProductResponse putProduct(ProductRequest productRequest)
            throws ProductNotFoundException, InvalidDataProvidedException {

        formatAndValidateProductDetails(productRequest);

        Product modifiedProduct = getProductObjectById(productRequest.getId());

        modifiedProduct.setName(productRequest.getName());
        modifiedProduct.setDescription(productRequest.getDescription());
        modifiedProduct.setImageUrl(productRequest.getImageUrl());
        modifiedProduct.setTax(productRequest.getTax());
        modifiedProduct.setPrice(productRequest.getPrice());
        modifiedProduct.setNetQuantity(productRequest.getNetQuantity());

        productRedisService.saveProduct(modifiedProduct);
        return mapProductResponse(inventoryRepository.save(modifiedProduct));
    }

    @Override
    public ProductResponse disableOrEnableProduct(int productId)
            throws ProductNotFoundException, InvalidDataProvidedException {
        Product product = getProductObjectById(productId);
        product.setAvailable(!product.isAvailable());

        productRedisService.saveProduct(product);
        inventoryRepository.save(product);
        return mapProductResponse(product);
    }


    @Override
    public boolean checkQuantity(int productId, int desiredQuantity) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException, ProductIsNotAvailableException {
        if(!(Utilities.validNumber(productId) && Utilities.validNumber(desiredQuantity))){
            throw new InvalidDataProvidedException("Numbers should be positive");
        }
        Optional<Product> result = inventoryRepository.findById(productId);

        if (!result.isPresent()) {
            throw new ProductNotFoundException("Invalid Product Id with " + productId);
        }

        Product originalProduct = result.get();

        if(!originalProduct.isAvailable()){
            throw new ProductIsNotAvailableException("The requested item is stopped by the Admin");
        }

        if (originalProduct.getNetQuantity() < desiredQuantity) {
            throw new NotEnoughQuanityException("Not enough Quantity");
        }
        return true;
    }

    @Override
    public boolean reduceQuantity(int productId, int desiredQuantity) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException {

        if(!(Utilities.validNumber(productId) && Utilities.validNumber(desiredQuantity))){
            throw new InvalidDataProvidedException("Numbers should be positive");
        }
        Optional<Product> result = inventoryRepository.findById(productId);

        if (!result.isPresent()) {
            throw new ProductNotFoundException("Invalid Product Id with " + productId);
        }

        Product originalProduct = result.get();

        if (originalProduct.getNetQuantity() < desiredQuantity) {
            throw new NotEnoughQuanityException("Not enough Quantity");
        }

        int updatedQuantity = originalProduct.getNetQuantity() - desiredQuantity;
        originalProduct.setNetQuantity(updatedQuantity);

        inventoryRepository.save(originalProduct);
        return true;
    }

    @Override
    public ProductDetailsResponse getProductDetail(int productId) throws ProductNotFoundException, InvalidDataProvidedException {
        Product product = getProductObjectById(productId);
        return mapProductDetailResponse(product);
    }

    @Override
    public List<ProductDetailsResponse> getAllProductDetails(List<Integer> productIds) throws ProductNotFoundException, InvalidDataProvidedException {

        ArrayList<Product> products = new ArrayList<>();

        for (Integer productId : productIds) {
            Product product = getProductObjectById(productId);
            products.add(product);
        }
        return mapProductDetailResponse(products);
    }

    private void formatAndValidateProductDetails(ProductRequest productRequest)
            throws InvalidDataProvidedException {
//        format Data
        productRequest.setName(Utilities.formatString(productRequest.getName()));
        productRequest.setDescription(Utilities.formatString(productRequest.getDescription()));

        if (!(productRequest.getId()>=0
                && Utilities.validNumber(productRequest.getPrice())
                && Utilities.validNumberEqualToZero(productRequest.getTax())
                && Utilities.validNumber(productRequest.getNetQuantity()))) {

            throw new InvalidDataProvidedException("Numbers Should Be Positive");
        }

        if(0 > productRequest.getTax() || 50 < productRequest.getTax() ){
            throw new InvalidDataProvidedException("Tax should be below 50%");
        }

        if (!(Utilities.validString(productRequest.getName())
                && Utilities.validString(productRequest.getDescription()))) {

            throw new InvalidDataProvidedException("Invalid String Provided");
        }
    }

    private boolean formatAndValidateProductDetails(List<ProductRequest> products)
            throws InvalidDataProvidedException {

        for (ProductRequest product : products) {
            formatAndValidateProductDetails(product);
        }
        return true;
    }

    private Product getProductFromRequest(ProductRequest product) {
        return modelMapper.map(product, Product.class);
    }

    private List<Product> getAllProductFromRequest(List<ProductRequest> products) {
        Type listType = new TypeToken<List<Product>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }

    private ProductResponse mapProductResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    private Page<ProductResponse> mapAllProductResponse(Page<Product> products) {
        return products.map(this::mapProductResponse);
    }

    private List<ProductResponse> mapAllProductResponse(List<Product> products) {
        Type listType = new TypeToken<List<ProductResponse>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }

    private Iterable<ProductResponse> mapAllSavedProductResponse(Iterable<Product> products) {
        Type listType = new TypeToken<Iterable<ProductResponse>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }

    private ProductDetailsResponse mapProductDetailResponse(Product product) {
        return modelMapper.map(product, ProductDetailsResponse.class);
    }
    private Page<ProductDetailsResponse> mapAllProductDetailResponse(Page<Product> products) {
        return products.map(this::mapProductDetailResponse);
    }

    private List<ProductDetailsResponse> mapProductDetailResponse(List<Product> product) {
        Type listType = new TypeToken<List<ProductDetailsResponse>>() {
        }.getType();
        return modelMapper.map(product, listType);
    }
}
