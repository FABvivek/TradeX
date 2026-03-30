package com.TradeX.order.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WalletClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public void lockBalance(Long userId, Double amount) {
        String url = "http://localhost:8082/wallet/lock/" + userId + "?amount=" + amount;
        restTemplate.postForObject(url, null, String.class);
    }
}
