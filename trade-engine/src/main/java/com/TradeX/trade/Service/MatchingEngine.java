package com.TradeX.trade.Service;

import com.TradeX.trade.Client.OrderClient;
import com.TradeX.trade.Client.WalletClient;
import com.TradeX.trade.DTO.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class MatchingEngine {

    private final WalletClient walletClient;
    private final OrderClient orderClient;

    private final PriorityQueue<OrderEvent> buyOrders =
            new PriorityQueue<>((a, b) -> Double.compare(b.getPrice(), a.getPrice())); // max heap

    private final PriorityQueue<OrderEvent> sellOrders =
            new PriorityQueue<>(Comparator.comparingDouble(OrderEvent::getPrice)); // min heap

    public void processOrder(OrderEvent order) {

        System.out.println("📥 Processing order: " + order);

        if ("BUY".equalsIgnoreCase(order.getType())) {
            matchBuy(order);
        } else {
            matchSell(order);
        }
    }

    private void matchBuy(OrderEvent buy) {

        while (!sellOrders.isEmpty() && buy.getPrice() >= sellOrders.peek().getPrice()) {

            OrderEvent sell = sellOrders.poll();

            double tradedQty = Math.min(buy.getQuantity(), sell.getQuantity());
            double tradedAmount = tradedQty * sell.getPrice();

            System.out.println("⚡ TRADE EXECUTED: Qty=" + tradedQty + " Price=" + sell.getPrice());

            try {
                // 💰 INR
                walletClient.deduct(buy.getUserId(), tradedAmount);
                walletClient.credit(sell.getUserId(), tradedAmount);

                // 🪙 BTC
                walletClient.deductBTC(sell.getUserId(), tradedQty);
                walletClient.creditBTC(buy.getUserId(), tradedQty);

                // 📊 Order
                orderClient.updateOrder(buy.getOrderId(), tradedQty);
                orderClient.updateOrder(sell.getOrderId(), tradedQty);

            } catch (Exception e) {
                System.out.println("❌ ERROR during trade: " + e.getMessage());
                throw e; // important for debugging
            }

            // 🔄 update quantities
            buy.setQuantity(buy.getQuantity() - tradedQty);
            sell.setQuantity(sell.getQuantity() - tradedQty);

            // 🔁 partial sell remains
            if (sell.getQuantity() > 0) {
                sellOrders.add(sell);
            }

            // 🛑 buy fully satisfied
            if (buy.getQuantity() == 0) return;
        }

        // 📌 no match → add to order book
        buyOrders.add(buy);
        System.out.println("📌 BUY added to order book");
    }

    private void matchSell(OrderEvent sell) {

        while (!buyOrders.isEmpty() && buyOrders.peek().getPrice() >= sell.getPrice()) {

            OrderEvent buy = buyOrders.poll();

            double tradedQty = Math.min(buy.getQuantity(), sell.getQuantity());
            double tradedAmount = tradedQty * sell.getPrice();

            System.out.println("⚡ TRADE EXECUTED: Qty=" + tradedQty + " Price=" + sell.getPrice());

            // 💰 WALLET SETTLEMENT

            try {
                // 💰 INR
                walletClient.deduct(buy.getUserId(), tradedAmount);
                walletClient.credit(sell.getUserId(), tradedAmount);

                // 🪙 BTC
                walletClient.deductBTC(sell.getUserId(), tradedQty);
                walletClient.creditBTC(buy.getUserId(), tradedQty);

                // 📊 Order
                orderClient.updateOrder(buy.getOrderId(), tradedQty);
                orderClient.updateOrder(sell.getOrderId(), tradedQty);

            } catch (Exception e) {
                System.out.println("❌ ERROR during trade: " + e.getMessage());
                throw e; // important for debugging
            }

            // 🔄 update quantities
            buy.setQuantity(buy.getQuantity() - tradedQty);
            sell.setQuantity(sell.getQuantity() - tradedQty);

            // 🔁 partial buy remains
            if (buy.getQuantity() > 0) {
                buyOrders.add(buy);
            }

            // 🛑 sell fully satisfied
            if (sell.getQuantity() == 0) return;
        }

        // 📌 no match → add to order book
        sellOrders.add(sell);
        System.out.println("📌 SELL added to order book");
    }
}
