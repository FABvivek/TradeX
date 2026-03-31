package com.TradeX.trade.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WalletClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public void deduct(Long userId, Double amount) {
        String url = "http://localhost:8082/wallet/deduct/" + userId + "?amount=" + amount;
        restTemplate.postForObject(url, null, String.class);
    }

    public void credit(Long userId, Double amount) {
        String url = "http://localhost:8082/wallet/credit/" + userId + "?amount=" + amount;
        restTemplate.postForObject(url, null, String.class);
    }

    public void deductBTC(Long userId, Double amount) {
        String url = "http://localhost:8082/wallet/btc/deduct/" + userId + "?amount=" + amount;
        restTemplate.postForObject(url, null, String.class);
    }

    public void creditBTC(Long userId, Double amount) {
        String url = "http://localhost:8082/wallet/btc/credit/" + userId + "?amount=" + amount;
        restTemplate.postForObject(url, null, String.class);
    }
}
