package com.TradeX.order.Service;

import com.TradeX.order.Client.WalletClient;
import com.TradeX.order.DTO.OrderEvent;
import com.TradeX.order.DTO.OrderRequest;
import com.TradeX.order.Entity.Order;
import com.TradeX.order.Kafka.OrderProducer;
import com.TradeX.order.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WalletClient walletClient;
    private final OrderProducer orderProducer;

    public Order placeOrder(OrderRequest request) {



        // 🧠 Step 1: Calculate total cost
        double totalAmount = request.getPrice() * request.getQuantity();

        // 🔒 Step 2: Lock money in wallet
        if ("BUY".equalsIgnoreCase(request.getType())) {
            double tAmount = request.getPrice() * request.getQuantity();
            walletClient.lockBalance(request.getUserId(), tAmount);
        } else {
            walletClient.lockBTC(request.getUserId(), request.getQuantity());
        }

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

        Order  savedorder  =orderRepository.save(order);
        OrderEvent  orderEvent = new OrderEvent(
                savedorder.getId(),
                savedorder.getUserId(),
                savedorder.getType(),
                savedorder.getPrice(),
                savedorder.getQuantity()
        );
        orderProducer.sendOrderEvent(orderEvent);
        return savedorder;
    }

    @Transactional
    public Order updateOrder(Long orderId, Double tradedQty) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        double newFilled = order.getFilledQuantity() + tradedQty;
        order.setFilledQuantity(newFilled);

        if (newFilled == order.getQuantity()) {
            order.setStatus("COMPLETED");
        } else {
            order.setStatus("PARTIAL");
        }

        return orderRepository.save(order);
    }
}
