package com.TradeX.order.Controller;

import com.TradeX.order.DTO.OrderRequest;
import com.TradeX.order.Entity.Order;
import com.TradeX.order.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @PostMapping("/update/{orderId}")
    public Order updateOrder(@PathVariable Long orderId,
                             @RequestParam Double qty) {
        return orderService.updateOrder(orderId, qty);
    }
}
