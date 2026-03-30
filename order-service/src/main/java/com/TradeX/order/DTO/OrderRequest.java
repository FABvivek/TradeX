package com.TradeX.order.DTO;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private String type;
    private Double price;
    private Double quantity;
}
