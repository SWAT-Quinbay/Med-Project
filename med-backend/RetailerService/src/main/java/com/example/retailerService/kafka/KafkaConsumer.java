package com.example.retailerService.kafka;


import com.example.retailerService.Service.RetailerService;
import com.example.retailerService.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    @Autowired
    private RetailerService retailerService;

    @KafkaListener(topics= Constants.RETAILER_KAFKA_TOPIC, groupId = Constants.RETAILER_KAFKA_GROUP_ID)
    public void consume(String message){
        try {
            log.info("Data Consumed By KAFKA "+ message);
            retailerService.incrementQuantityViaKafka(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}