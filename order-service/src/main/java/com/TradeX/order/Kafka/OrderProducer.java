package com.TradeX.order.Kafka;

import com.TradeX.order.DTO.OrderEvent;
import com.TradeX.order.Entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrderEvent(OrderEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-created", json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize OrderEvent", e);
        }
    }
}
