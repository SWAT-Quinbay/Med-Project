package com.example.dealerService.kafka;


import com.example.dealerService.service.DealerService;
import com.example.dealerService.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    @Autowired
    private DealerService dealerService;

    @KafkaListener(topics= Constants.DEALER_KAFKA_TOPIC, groupId = Constants.DEALER_KAFKA_GROUP_ID)
    public void consume(String message){
        try {
            log.info("Data Consumed By KAFKA "+ message);
            dealerService.incrementQuantityViaKafka(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}