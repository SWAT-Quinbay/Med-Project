package com.example.salesService.Service;

import com.example.salesService.CustomException.InvalidDataProvidedException;
import com.example.salesService.CustomException.NotEnoughQuantityException;
import com.example.salesService.Repository.SalesRepository;
import com.example.salesService.dto.request.ItemCheckRequest;
import com.example.salesService.dto.request.OrderRequest;
import com.example.salesService.dto.response.OrderResponse;
import com.example.salesService.dto.response.ProductDetailsResponse;
import com.example.salesService.model.Order;
import com.example.salesService.model.OrderItem;
import com.example.salesService.security.SecurityConstants;
import com.example.salesService.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SalesServiceImp implements SalesService {
    @Autowired
    SalesRepository salesRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${ADMIN_BASE_URL}")
    private String ADMIN_BASE_URL;

    @Value("${RETAILER_BASE_URL}")
    private String RETAILER_BASE_URL;

    @Override
    public List<Order> searchByRetailerId(int retailerId, String query) {
        Query customQuery = new Query();
        customQuery.addCriteria(Criteria.where("retailerId").is(retailerId));
        if(query != null && !query.isEmpty()){
            int orderId = Integer.parseInt(query);
            customQuery.addCriteria(Criteria.where("id").is(orderId));
        }
        customQuery.with(Sort.by("createdTime").descending());
        return mongoTemplate.find(customQuery, Order.class);
    }

    @Override
    public OrderResponse add(OrderRequest orderRequest, String token) throws InvalidDataProvidedException, NotEnoughQuantityException {
        validateData(orderRequest);
        checkQuantityAndReduceIt(orderRequest,token);
        Order order = modelMapper.map(orderRequest,Order.class);
        order.setStatus("CREATED");

        populateProductDetails(order,token);

        order.setId(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));

        return modelMapper.map(salesRepository.save(order),OrderResponse.class);
    }

    private void checkQuantityAndReduceIt(OrderRequest order,String token) throws NotEnoughQuantityException {
        List<ItemCheckRequest> requests = new ArrayList<>();

        for(OrderItem item : order.getOrderItems()){
            requests.add(
                    ItemCheckRequest.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .build()
            );
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.AUTH_HEADER, token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<List<ItemCheckRequest>> entity = new HttpEntity<>(requests, headers);

        boolean result = Boolean.TRUE.equals(
                restTemplate.exchange(
                        RETAILER_BASE_URL + "/retailer/stock/check",
                        HttpMethod.POST,
                        entity,
                        Boolean.class)
                        .getBody());
        if(result){
            //have valid stock
            boolean hasReduced  = Boolean.TRUE.equals(
                    restTemplate.exchange(
                            RETAILER_BASE_URL + "/retailer/stock/reduce",
                            HttpMethod.POST,
                            entity,
                            Boolean.class)
                            .getBody());

            log.info("Product quantity reduced "+hasReduced+"  "+ requests);
        }else {
            throw new NotEnoughQuantityException("Not Enough Quantity in inventory");
        }
    }

    private void populateProductDetails(Order order,String token) {
        for (OrderItem item : order.getOrderItems()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(SecurityConstants.AUTH_HEADER, token);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>("body", headers);

            ResponseEntity<ProductDetailsResponse> response = restTemplate.exchange(
                    ADMIN_BASE_URL + "/inventory/detail?id=" + item.getProductId(),
                    HttpMethod.GET,
                    entity,
                    ProductDetailsResponse.class);
            ProductDetailsResponse productDetailsResponse = response.getBody();

            assert productDetailsResponse != null;

            item.setName(productDetailsResponse.getName());
            item.setDescription(productDetailsResponse.getDescription());
            item.setTax(productDetailsResponse.getTax());
            item.setPrice(productDetailsResponse.getPrice());
            item.setImageUrl(productDetailsResponse.getImageUrl());
        }
    }

    private void validateData(OrderRequest order) throws InvalidDataProvidedException {
        if (!Utilities.validNumber(order.getRetailerId())) {
            throw new InvalidDataProvidedException("Invalid Retailer Id");
        }
        for (OrderItem item : order.getOrderItems()) {
            if (!(Utilities.validNumber(item.getProductId()) && Utilities.validNumber(item.getQuantity()))) {
                throw new InvalidDataProvidedException("Invalid Product Id");
            }
        }

        //format string
        order.setPaymentMethod(Utilities.formatString(order.getPaymentMethod()));

        if (!(Utilities.validString(order.getPaymentMethod()))) {
            throw new InvalidDataProvidedException("Invalid String provided");
        }
    }
}
