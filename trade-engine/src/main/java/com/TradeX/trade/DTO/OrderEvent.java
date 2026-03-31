package com.TradeX.trade.DTO;

import lombok.Data;

@Data
public class OrderEvent {
    private Long orderId;
    private Long userId;
    private String type; // BUY / SELL
    private Double price;
    private Double quantity;
}
