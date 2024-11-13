package com.i8ai.training.store.service;

import com.i8ai.training.store.service.data.Balance;

import java.time.LocalDateTime;
import java.util.List;

public interface BalanceService {

    Balance getNetBalance(LocalDateTime start, LocalDateTime end);

    List<Balance> getBalancesPerProduct(LocalDateTime start, LocalDateTime end);

    List<Balance> getBalancesPerShop(LocalDateTime start, LocalDateTime end);

    List<Balance> getBalancesPerProductPerShop(LocalDateTime start, LocalDateTime end);

    Balance getBalanceByProduct(Long productId, LocalDateTime start, LocalDateTime end);

    Balance getBalanceByShop(Long shopId, LocalDateTime start, LocalDateTime end);

    List<Balance> getBalancesByProductPerShop(Long productId, LocalDateTime start, LocalDateTime end);

    List<Balance> getBalancesByShopPerProduct(Long shopId, LocalDateTime start, LocalDateTime end);

    Balance getBalanceByProductAndShop(Long productId, Long shopId, LocalDateTime start, LocalDateTime end);
}
