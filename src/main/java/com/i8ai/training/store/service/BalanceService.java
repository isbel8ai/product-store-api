package com.i8ai.training.store.service;

import com.i8ai.training.store.rest.dto.BalanceDto;

import java.time.LocalDateTime;
import java.util.List;

public interface BalanceService {

    BalanceDto getNetBalance(LocalDateTime start, LocalDateTime end);

    List<BalanceDto> getBalancesPerProduct(LocalDateTime start, LocalDateTime end);

    List<BalanceDto> getBalancesPerShop(LocalDateTime start, LocalDateTime end);

    List<BalanceDto> getBalancesPerProductPerShop(LocalDateTime start, LocalDateTime end);

    BalanceDto getBalanceByProduct(Long productId, LocalDateTime start, LocalDateTime end);

    BalanceDto getBalanceByShop(Long shopId, LocalDateTime start, LocalDateTime end);

    List<BalanceDto> getBalancesByProductPerShop(Long productId, LocalDateTime start, LocalDateTime end);

    List<BalanceDto> getBalancesByShopPerProduct(Long shopId, LocalDateTime start, LocalDateTime end);

    BalanceDto getBalanceByProductAndShop(Long productId, Long shopId, LocalDateTime start, LocalDateTime end);
}
