package com.TradeX.order.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private Long orderId;
    private Long userId;
    private String type; // BUY / SELL
    private Double price;
    private Double quantity;
}
