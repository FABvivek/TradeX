package com.TradeX.wallet.Controller;

import com.TradeX.wallet.Entity.Wallet;
import com.TradeX.wallet.Service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/create/{userId}")
    public Wallet create(@PathVariable Long userId) {
        return walletService.createWallet(userId);
    }

    @PostMapping("/add/{userId}")
    public Wallet addMoney(@PathVariable Long userId,
                           @RequestParam Double amount) {
        return walletService.addMoney(userId, amount);
    }

    @GetMapping("/{userId}")
    public Wallet get(@PathVariable Long userId) {
        return walletService.getWallet(userId);
    }
    @PostMapping("/lock/{userId}")
    public Wallet lock(@PathVariable Long userId,
                       @RequestParam Double amount) {
        return walletService.lockBalance(userId, amount);
    }

    @PostMapping("/unlock/{userId}")
    public Wallet unlock(@PathVariable Long userId,
                         @RequestParam Double amount) {
        return walletService.unlockBalance(userId, amount);
    }

    @PostMapping("/deduct/{userId}")
    public Wallet deduct(@PathVariable Long userId,
                         @RequestParam Double amount) {
        return walletService.deductLockedBalance(userId, amount);
    }
}
