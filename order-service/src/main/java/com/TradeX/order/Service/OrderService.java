package com.TradeX.order.Service;

import com.TradeX.order.Client.WalletClient;
import com.TradeX.order.DTO.OrderRequest;
import com.TradeX.order.Entity.Order;
import com.TradeX.order.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WalletClient walletClient;

    public Order placeOrder(OrderRequest request) {

        // 🧠 Step 1: Calculate total cost
        double totalAmount = request.getPrice() * request.getQuantity();

        // 🔒 Step 2: Lock money in wallet
        walletClient.lockBalance(request.getUserId(), totalAmount);

        // 📦 Step 3: Save order
        Order order = Order.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .filledQuantity(0.0)
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }
}
