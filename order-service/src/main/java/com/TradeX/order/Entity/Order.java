package com.TradeX.order.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String type; // BUY / SELL

    private Double price;
    private Double quantity;

    @Column(nullable = false)
    private Double filledQuantity = 0.0;

    @Column(nullable = false)
    private String status; // OPEN, PARTIAL, COMPLETED

    private LocalDateTime createdAt;
}
