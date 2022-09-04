package com.example.adminService.services.implement;

import com.example.adminService.customExceptions.*;
import com.example.adminService.dto.requests.StockRequest;
import com.example.adminService.dto.requests.StockRequestStatusRequest;
import com.example.adminService.dto.responses.StockRequestResponse;
import com.example.adminService.dto.responses.StockUpdateResponse;
import com.example.adminService.models.AppUser;
import com.example.adminService.models.StockRequestEntity;
import com.example.adminService.repos.StockRequestRepository;
import com.example.adminService.security.SecurityConstants;
import com.example.adminService.services.InventoryService;
import com.example.adminService.services.StockRequestService;
import com.example.adminService.services.UserService;
import com.example.adminService.utils.Constants;
import com.example.adminService.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StockRequestServiceImp implements StockRequestService {
    @Autowired
    StockRequestRepository stockRequestRepository;

    @Autowired
    UserService userService;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EntityManager entityManager;

    @Value("${DEALER_BASE_URL}")
    private String DEALER_BASE_URL;
    private ObjectMapper objectMapper;

    StockRequestServiceImp() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<StockRequestResponse> getOrdersForUser(int userId, String requestId, String status)
            throws InvalidDataProvidedException, ProductNotFoundException, UserNotFoundException {
        List<StockRequestEntity> result = customSearch("receiver", userId, requestId, status);
        return mapAllStockEntity(result);
    }

    @Override
    public List<StockRequestResponse> getOrdersHistoryForUser(int userId, String requestId, String status)
            throws InvalidDataProvidedException, ProductNotFoundException, UserNotFoundException {
        List<StockRequestEntity> result = customSearch("sender", userId, requestId, status);
        return mapAllStockEntity(result);
    }

    private List<StockRequestEntity> customSearch(String colName, int userId, String requestId, String status) throws UserNotFoundException, InvalidDataProvidedException {


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockRequestEntity> criteriaQuery = criteriaBuilder.createQuery(StockRequestEntity.class);

        Root<StockRequestEntity> stockRequestEntityRoot = criteriaQuery.from(StockRequestEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        AppUser user = userService.getUserObjectById(userId);

        Predicate userIdCheck = criteriaBuilder.equal(stockRequestEntityRoot.get(colName), user);
        predicates.add(userIdCheck);

        if (requestId != null && !requestId.isEmpty()) {
            Predicate requestIdCheck = criteriaBuilder.equal(stockRequestEntityRoot.get("id"), Integer.parseInt(requestId));
            predicates.add(requestIdCheck);
        }

        if (!status.isEmpty()) {
            status = status.replace("[^A-Z]", "");
            Predicate requestIdCheck = criteriaBuilder.equal(stockRequestEntityRoot.get("status"), status);
            predicates.add(requestIdCheck);
        }

        Predicate finalQuery = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        criteriaQuery.where(finalQuery);
        criteriaQuery.orderBy(criteriaBuilder.desc(stockRequestEntityRoot.get("createdAt")));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public StockRequestResponse addAdminStockRequest(StockRequest stockRequest) throws UserNotFoundException, InvalidDataProvidedException, ProductNotFoundException, NotEnoughQuanityException {

        validateStockRequest(stockRequest, true);
        StockRequestEntity stockRequestEntity = createEntity(stockRequest, true);
        return mapStockEntity(stockRequestRepository.save(stockRequestEntity));
    }

    @Override
    public StockRequestResponse addDealerStockRequest(StockRequest stockRequest) throws InvalidDataProvidedException, UserNotFoundException, ProductNotFoundException, NotEnoughQuanityException {
        validateStockRequest(stockRequest, false);
        StockRequestEntity stockRequestEntity = createEntity(stockRequest, false);
        return mapStockEntity(stockRequestRepository.save(stockRequestEntity));
    }

    private StockRequestEntity createEntity(StockRequest stockRequest, boolean isAdmin) throws UserNotFoundException, InvalidDataProvidedException, NotEnoughQuanityException, ProductNotFoundException {

        StockRequestEntity stockRequestEntity = new StockRequestEntity();

        stockRequestEntity.setProductId(stockRequest.getProductId());
        stockRequestEntity.setRequestedQuantity(stockRequest.getRequestedQuantity());
        stockRequestEntity.setStatus(Constants.PENDING_STATUS);
        stockRequestEntity.setSender(userService.getUserObjectById(stockRequest.getSenderId()));
        if (isAdmin) {
            //check admin inventory Quantity
            inventoryService.checkQuantity(stockRequest.getProductId(), stockRequest.getRequestedQuantity());
            stockRequestEntity.setReceiver(userService.getAdminObject());
        } else {
            //check dealer inventory
            Boolean result = restTemplate.getForObject(
                    DEALER_BASE_URL + "/dealer/check?dealerId=" + stockRequest.getReceiverId()
                            + "&productId=" + stockRequest.getProductId()
                            + "&quantity=" + stockRequest.getRequestedQuantity()
                    , Boolean.class);

            if (Boolean.FALSE.equals(result)) {
                throw new NotEnoughQuanityException("Not Enough Quantity in inventory");
            }

            stockRequestEntity.setReceiver(userService.getUserObjectById(stockRequest.getReceiverId()));
        }
        return stockRequestEntity;
    }

    @Override
    public StockRequestResponse updateStockRequestStatus(StockRequestStatusRequest request, String token)
            throws Exception {

        Optional<StockRequestEntity> result = stockRequestRepository.findById(request.getRequestId());
        if (!result.isPresent()) {
            throw new StockRequestNotFoundException("Record not found");
        }

        StockRequestEntity stockRequestEntity = result.get();
        if (!stockRequestEntity.getStatus().equals(Constants.PENDING_STATUS)) {
            throw new RequestAlreadyRespondedException("Cannot override the responded status");
        }

        if (request.isApproved()) {
            //reduce Quantity
            if (stockRequestEntity.getReceiver().getRole().equals(Constants.ADMIN_ROLE)) {
                inventoryService.checkQuantity(stockRequestEntity.getProductId(), stockRequestEntity.getRequestedQuantity());
                inventoryService.reduceQuantity(
                        stockRequestEntity.getProductId(),
                        stockRequestEntity.getRequestedQuantity());
            } else {
                //check dealer inventory
                Boolean haveStock = restTemplate.getForObject(
                        DEALER_BASE_URL + "/dealer/check?dealerId=" + stockRequestEntity.getReceiver().getId()
                                + "&productId=" + stockRequestEntity.getProductId()
                                + "&quantity=" + stockRequestEntity.getRequestedQuantity()
                        , Boolean.class);

                if (Boolean.FALSE.equals(haveStock)) {
                    throw new NotEnoughQuanityException("Not Enough Quantity in inventory");
                }
                //reduce in dealer

                HttpHeaders headers = new HttpHeaders();
                headers.add(SecurityConstants.AUTH_HEADER, token);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                HttpEntity<String> entity = new HttpEntity<>("body", headers);

                boolean hasReduced = Boolean.TRUE.equals( restTemplate.exchange(
                        DEALER_BASE_URL + "/dealer/reduce?dealerId=" + stockRequestEntity.getReceiver().getId()
                                + "&productId=" + stockRequestEntity.getProductId()
                                + "&quantity=" + stockRequestEntity.getRequestedQuantity()
                        ,
                        HttpMethod.GET,
                        entity,
                        Boolean.class).getBody());

                if (!hasReduced) {
                    throw new Exception("Error occurred in reducing quantity in dealer inventory");
                }

            }
            //update in consumer
            triggerEventToStockUpdate(stockRequestEntity);
            stockRequestEntity.setStatus(Constants.APPROVED_STATUS);
        } else {
            stockRequestEntity.setStatus(Constants.DENIED_STATUS);
        }
        stockRequestEntity.setRemark(Utilities.formatString(request.getRemark()));

        return mapStockEntity(stockRequestRepository.save(stockRequestEntity));
    }

    private void triggerEventToStockUpdate(StockRequestEntity stockRequestEntity) throws JsonProcessingException {

        StockUpdateResponse response = StockUpdateResponse.builder()
                .senderId(stockRequestEntity.getSender().getId())
                .productId(stockRequestEntity.getProductId())
                .quantity(stockRequestEntity.getRequestedQuantity())
                .build();

        String payload = objectMapper.writeValueAsString(response);

        if (stockRequestEntity.getSender().getRole().equals(Constants.DEALER_ROLE)) {
            // produce event for dealer
            kafkaTemplate.send(Constants.DEALER_KAFKA_TOPIC, Utilities.generateUuid(), payload);
            log.info("Data Published for Dealer through KAFKA " + payload);
        } else {
            //produce event for retailer
            kafkaTemplate.send(Constants.RETAILER_KAFKA_TOPIC, Utilities.generateUuid(), payload);
            log.info("Data Published for Retailer through KAFKA " + payload);
        }
    }

    private void validateStockRequest(StockRequest stockRequest, boolean isAdmin) throws InvalidDataProvidedException {
        if (!(
                Utilities.validNumber(stockRequest.getProductId())
                        && Utilities.validNumber(stockRequest.getRequestedQuantity())
                        && Utilities.validNumber(stockRequest.getSenderId())
                        && (isAdmin || Utilities.validNumber(stockRequest.getReceiverId()))
        )) {
            throw new InvalidDataProvidedException("Numbers Should be Positive");
        }
        if (stockRequest.getReceiverId() == stockRequest.getSenderId()) {
            throw new InvalidDataProvidedException("Sender and Receiver can't be same");
        }
    }

    private StockRequestResponse mapStockEntity(StockRequestEntity stockRequestEntity) throws InvalidDataProvidedException, ProductNotFoundException {
        return StockRequestResponse.builder()
                .id(stockRequestEntity.getId())
                .createdAt(stockRequestEntity.getCreatedAt())
                .status(stockRequestEntity.getStatus())
                .remark(stockRequestEntity.getRemark())
                .requestedQuantity(stockRequestEntity.getRequestedQuantity())
                .product(inventoryService.getProductDetail(stockRequestEntity.getProductId()))

                .senderId(stockRequestEntity.getSender().getId())
                .senderName(stockRequestEntity.getSender().getName())
                .senderRole(stockRequestEntity.getSender().getRole())

                .receiverId(stockRequestEntity.getReceiver().getId())
                .receiverName(stockRequestEntity.getReceiver().getName())
                .receiverRole(stockRequestEntity.getReceiver().getRole())
                .build();
    }

    private List<StockRequestResponse> mapAllStockEntity(List<StockRequestEntity> stocks) throws InvalidDataProvidedException, ProductNotFoundException {
        List<StockRequestResponse> responses = new ArrayList<>();
        for (StockRequestEntity e : stocks) {
            responses.add(mapStockEntity(e));
        }
        return responses;
    }
}
