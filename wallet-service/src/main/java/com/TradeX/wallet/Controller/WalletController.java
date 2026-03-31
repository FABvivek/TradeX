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
    @PostMapping("/credit/{userId}")
    public Wallet credit(@PathVariable Long userId,
                         @RequestParam Double amount) {
        return walletService.addMoney(userId, amount);
    }

    @PostMapping("/btc/add/{userId}")
    public Wallet addBTC(@PathVariable Long userId,
                         @RequestParam Double amount) {
        return walletService.addBTC(userId, amount);
    }

    @PostMapping("/btc/lock/{userId}")
    public Wallet lockBTC(@PathVariable Long userId,
                          @RequestParam Double amount) {
        return walletService.lockBTC(userId, amount);
    }

    @PostMapping("/btc/deduct/{userId}")
    public Wallet deductBTC(@PathVariable Long userId,
                            @RequestParam Double amount) {
        return walletService.deductBTC(userId, amount);
    }

    @PostMapping("/btc/credit/{userId}")
    public Wallet creditBTC(@PathVariable Long userId,
                            @RequestParam Double amount) {
        return walletService.creditBTC(userId, amount);
    }
}
