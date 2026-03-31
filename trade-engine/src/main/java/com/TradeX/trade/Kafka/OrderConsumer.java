package com.TradeX.trade.Kafka;

import com.TradeX.trade.DTO.OrderEvent;
import com.TradeX.trade.Service.MatchingEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {


    private final MatchingEngine matchingEngine;

    private final ObjectMapper objectMapper;




    public OrderConsumer(ObjectMapper objectMapper, MatchingEngine matchingEngine) {
        this.objectMapper = objectMapper;
        this.matchingEngine = matchingEngine;
    }

    @KafkaListener(topics = "order-created", groupId = "trade-group")
    public void consume(String message) {
        try {
            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
            System.out.println("Received: " + event);

            // 👉 Matching logic will go here later
            matchingEngine.processOrder(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize OrderEvent", e);
        }
    }
}
