package com.TradeX.trade.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public void updateOrder(Long orderId, Double qty) {
        String url = "http://localhost:8083/order/update/" + orderId + "?qty=" + qty;
        restTemplate.postForObject(url, null, String.class);
    }
}
