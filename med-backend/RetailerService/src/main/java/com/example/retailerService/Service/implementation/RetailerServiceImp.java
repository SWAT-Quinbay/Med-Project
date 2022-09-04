package com.example.retailerService.Service.implementation;

import com.example.retailerService.CustomException.InvalidDataProvidedException;
import com.example.retailerService.CustomException.NotEnoughQuantityException;
import com.example.retailerService.CustomException.ProductNotFoundException;
import com.example.retailerService.Model.Retailer;
import com.example.retailerService.Repository.RetailerRepository;
import com.example.retailerService.Service.RetailerService;
import com.example.retailerService.dto.request.OrderItem;
import com.example.retailerService.dto.request.RetailerRequest;
import com.example.retailerService.dto.response.ProductDetailsResponse;
import com.example.retailerService.dto.response.RetailerResponse;
import com.example.retailerService.dto.response.StockUpdateResponse;
import com.example.retailerService.security.SecurityConstants;
import com.example.retailerService.utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RetailerServiceImp implements RetailerService {
    @Autowired
    RetailerRepository retailerRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${ADMIN_BASE_URL}")
    private String ADMIN_BASE_URL;

    @Override
    public RetailerResponse addRetailerProduct(RetailerRequest retailerRequest, String token) throws InvalidDataProvidedException {
        validateDealerDetails(retailerRequest);
        Retailer retailer = getRetailerFromRequest(retailerRequest);
        return mapRetailerResponse(retailerRepository.save(retailer), token);
    }

    public List<RetailerResponse> getRetailerProducts(int retailerId, String query, String token) {
        query = Utilities.formatString(query);
        return mapAllRetailerResponse(
                retailerRepository.findByRetailerId(retailerId, Sort.by("stockInHand").descending())
                , query
                , token);
    }

    private void validateDealerDetails(RetailerRequest dealerRequest)
            throws InvalidDataProvidedException {

        if (!(Utilities.validNumber(dealerRequest.getProductId())
                && Utilities.validNumber(dealerRequest.getRetailerId())
                && Utilities.validNumber(dealerRequest.getStockInHand()))) {

            throw new InvalidDataProvidedException("Numbers Should Be Positive");
        }
    }


    @Override
    public boolean checkStock(int retailerId, List<OrderItem> orderItems) throws ProductNotFoundException, NotEnoughQuantityException, InvalidDataProvidedException {

        for (OrderItem item : orderItems) {
            if (!(Utilities.validNumber(item.getProductId()) && Utilities.validNumber(item.getQuantity()))) {
                throw new InvalidDataProvidedException("Number Should be Positive");
            }
            Optional<Retailer> retailer = retailerRepository.findByRetailerIdAndProductId(retailerId, item.getProductId());
            if (!retailer.isPresent()) {
                throw new ProductNotFoundException("Invalid Product Id");
            }
            if (retailer.get().getStockInHand() < item.getQuantity()) {
                throw new NotEnoughQuantityException("Not enough quantity in inventory");
            }
        }
        return true;
    }

    @Override
    public boolean reduceStock(int retailerId, List<OrderItem> orderItems) throws ProductNotFoundException, NotEnoughQuantityException, InvalidDataProvidedException {

        checkStock(retailerId, orderItems);

        for (OrderItem item : orderItems) {
            Optional<Retailer> result = retailerRepository.findByRetailerIdAndProductId(retailerId, item.getProductId());
            Retailer retailer = result.get();
            retailer.setStockInHand(retailer.getStockInHand() - item.getQuantity());
            retailerRepository.save(retailer);
        }
        return true;
    }

    @Override
    public void incrementQuantityViaKafka(String payload) {
        try {
            StockUpdateResponse response = objectMapper.readValue(payload, StockUpdateResponse.class);

            Optional<Retailer> result = retailerRepository.findByRetailerIdAndProductId(response.getSenderId(), response.getProductId());
            Retailer retailer;
            if (result.isPresent()) {
                retailer = result.get();
                retailer.setStockInHand(retailer.getStockInHand() + response.getQuantity());
            } else {
                retailer = new Retailer();
                retailer.setRetailerId(response.getSenderId());
                retailer.setProductId(response.getProductId());
                retailer.setStockInHand(response.getQuantity());
            }
            retailerRepository.save(retailer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Retailer getRetailerFromRequest(RetailerRequest retailerRequest) {
        Retailer retailer = new Retailer();
        retailer.setProductId(retailerRequest.getProductId());
        retailer.setRetailerId(retailerRequest.getRetailerId());
        retailer.setStockInHand(retailerRequest.getStockInHand());
        return retailer;
    }

    private RetailerResponse mapRetailerResponse(Retailer retailer, String token) {
        RetailerResponse retailerResponse = modelMapper.map(retailer, RetailerResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.AUTH_HEADER, token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<ProductDetailsResponse> response = restTemplate.exchange(
                ADMIN_BASE_URL + "/inventory/detail?id=" + retailerResponse.getProductId(),
                HttpMethod.GET,
                entity,
                ProductDetailsResponse.class);
        ProductDetailsResponse productDetailsResponse = response.getBody();

        retailerResponse.setProductDetail(productDetailsResponse);
        return retailerResponse;
    }

    private List<RetailerResponse> mapAllRetailerResponse(List<Retailer> retailers, String query, String token) {
        List<RetailerResponse> responses = new ArrayList<>();
        for (Retailer e : retailers) {
            RetailerResponse retailerResponse = mapRetailerResponse(e, token);
            if (query == null || retailerResponse.getProductDetail().getName().contains(query)) {
                responses.add(retailerResponse);
            }
        }
        return responses;
    }

}
