package com.TradeX.wallet.Service;

import com.TradeX.wallet.Entity.Wallet;
import com.TradeX.wallet.Repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    // Create wallet
    public Wallet createWallet(Long userId) {
        Wallet wallet = Wallet.builder()
                .userId(userId)
                .availableBalance(0.0)
                .lockedBalance(0.0)
                .btcBalance(0.0)
                .btcLocked(0.0)
                .build();

        return walletRepository.save(wallet);
    }

    // Add money
    public Wallet addMoney(Long userId, Double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setAvailableBalance(wallet.getAvailableBalance() + amount);
        return walletRepository.save(wallet);
    }

    // Get wallet
    public Wallet getWallet(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    // LOCK
    @Transactional
    public Wallet lockBalance(Long userId, Double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getAvailableBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setAvailableBalance(wallet.getAvailableBalance() - amount);
        wallet.setLockedBalance(wallet.getLockedBalance() + amount);

        return walletRepository.save(wallet);
    }

    //  UNLOCK
    @Transactional
    public Wallet unlockBalance(Long userId, Double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setLockedBalance(wallet.getLockedBalance() - amount);
        wallet.setAvailableBalance(wallet.getAvailableBalance() + amount);

        return walletRepository.save(wallet);
    }

    //  DEDUCT
    @Transactional
    public Wallet deductLockedBalance(Long userId, Double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setLockedBalance(wallet.getLockedBalance() - amount);

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet creditBalance(Long userId, Double amount) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setAvailableBalance(wallet.getAvailableBalance() + amount);

        return walletRepository.save(wallet);
    }
    @Transactional
    public Wallet addBTC(Long userId, Double amount) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBtcBalance(wallet.getBtcBalance() + amount);

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet lockBTC(Long userId, Double amount) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBtcBalance() < amount) {
            throw new RuntimeException("Insufficient BTC");
        }

        wallet.setBtcBalance(wallet.getBtcBalance() - amount);
        wallet.setBtcLocked(wallet.getBtcLocked() + amount);

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet deductBTC(Long userId, Double amount) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBtcLocked() < amount) {
            throw new RuntimeException("Insufficient locked BTC");
        }

        wallet.setBtcLocked(wallet.getBtcLocked() - amount);

        return walletRepository.save(wallet);
    }
    @Transactional
    public Wallet creditBTC(Long userId, Double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBtcBalance(wallet.getBtcBalance() + amount);
        return walletRepository.save(wallet);
    }
}
