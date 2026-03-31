package com.TradeX.wallet.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double availableBalance;

    private Double lockedBalance;

    @Column(nullable = false)
    private Double btcBalance = 0.0;

    @Column(nullable = false)
    private Double btcLocked = 0.0;
}
