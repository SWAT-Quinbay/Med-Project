package com.example.dealerService.serviceImplementation;

import com.example.dealerService.customException.InvalidDataProvidedException;
import com.example.dealerService.dto.requests.DealerRequest;
import com.example.dealerService.dto.responses.DealerResponse;
import com.example.dealerService.dto.responses.ProductDetailsResponse;
import com.example.dealerService.dto.responses.StockUpdateResponse;
import com.example.dealerService.repos.DealerRepository;
import com.example.dealerService.model.*;
import com.example.dealerService.security.SecurityConstants;
import com.example.dealerService.service.DealerService;
import com.example.dealerService.utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DealerServiceImp implements DealerService {

    @Autowired
    DealerRepository dealerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    @Value("${ADMIN_BASE_URL}")
    private String ADMIN_BASE_URL;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public DealerResponse addDealerProduct(DealerRequest dealerRequest,String token) throws InvalidDataProvidedException {
        validateDealerDetails(dealerRequest);
        Dealer dealer = getDealerFromRequest(dealerRequest);
        return mapDealerResponse( dealerRepository.save(dealer),token);
    }

    @Override
    public Page<DealerResponse> getDealerProducts(int dealerId,int page,int size,String token){
        PageRequest pr = PageRequest.of(page,size, Sort.by("stockInHand").descending());
        return mapAllDealerResponse(dealerRepository.findByDealerId(dealerId,pr),token);
    }

   @Override
   public List<DealerResponse> searchAllDealers(String query,int page, int size, String token){
       return mapAllDealerResponse(
               query,
               dealerRepository.findAll(Sort.by("stockInHand").descending()),
               token);
   }
    private void validateDealerDetails(DealerRequest dealerRequest)
            throws InvalidDataProvidedException {

        if (!(Utilities.validNumber(dealerRequest.getProductId())
                && Utilities.validNumber(dealerRequest.getDealerId())
                && Utilities.validNumber(dealerRequest.getStockInHand()))) {

            throw new InvalidDataProvidedException("Numbers Should Be Positive");
        }
    }

    @Override
    public void incrementQuantityViaKafka(String payload) {
        try {
            StockUpdateResponse response = objectMapper.readValue(payload,StockUpdateResponse.class);

            Optional<Dealer> result = dealerRepository.findByDealerIdAndProductId(response.getSenderId(),response.getProductId());
            Dealer dealer ;
            if(result.isPresent()){
                dealer =result.get();
                dealer.setStockInHand(dealer.getStockInHand()+response.getQuantity());
            }else{
                dealer = new Dealer();
                dealer.setDealerId(response.getSenderId());
                dealer.setProductId(response.getProductId());
                dealer.setStockInHand(response.getQuantity());
            }
            dealerRepository.save(dealer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkProductStock(int dealerId,int productId, int desiredQuantity) {
        Optional<Dealer> dealer = dealerRepository.findByDealerIdAndProductId(dealerId,productId);
        if(dealer.isPresent() && dealer.get().getStockInHand() >= desiredQuantity ){
            return true;
        }
        return false;
    }
    @Override
    public boolean reduceProductStock(int dealerId,int productId, int desiredQuantity) {
        Optional<Dealer> result = dealerRepository.findByDealerIdAndProductId(dealerId,productId);
        if(!result.isPresent() || result.get().getStockInHand() < desiredQuantity ){
            return false;
        }

        Dealer dealer = result.get();
        dealer.setStockInHand(dealer.getStockInHand() - desiredQuantity);
        dealerRepository.save(dealer);
        return true;
    }

    private Dealer getDealerFromRequest(DealerRequest dealerRequest) {

        Dealer dealer = new Dealer();
        dealer.setProductId(dealerRequest.getProductId());
        dealer.setDealerId(dealerRequest.getDealerId());
        dealer.setStockInHand(dealerRequest.getStockInHand());
        return dealer;
    }

    private List<Dealer> getAllDealersFromRequest(List<DealerRequest> dealerRequests)
    {
        List<Dealer> dealers = new ArrayList<>();
        for (DealerRequest dealerRequest: dealerRequests) {
            dealers.add(getDealerFromRequest(dealerRequest));
        }
        return dealers;
    }

    private DealerResponse mapDealerResponse(Dealer dealer,String token) {
        DealerResponse dealerResponse =  modelMapper.map(dealer, DealerResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.AUTH_HEADER,token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<ProductDetailsResponse> response =  restTemplate.exchange(
                        ADMIN_BASE_URL+"/inventory/detail?id="+dealerResponse.getProductId(),
                        HttpMethod.GET,
                        entity,
                ProductDetailsResponse.class);
        ProductDetailsResponse productDetailsResponse = response.getBody();

        dealerResponse.setProductDetail(productDetailsResponse);
        return dealerResponse;
    }

    private Page<DealerResponse> mapAllDealerResponse(Page<Dealer> dealers,String token) {
        return dealers.map((dealer)->mapDealerResponse(dealer,token));
    }
    private List<DealerResponse> mapAllDealerResponse(String query,List<Dealer> dealers,String token) {

        List<DealerResponse> responses = new ArrayList<>();
        for(Dealer dealer :dealers){
            DealerResponse  dealerResponse = mapDealerResponse(dealer,token);
            if(query == null || query.isEmpty() || dealerResponse.getProductDetail().getName().contains(query)){
                responses.add(dealerResponse);
            }
        }
        return responses;
    }


}
