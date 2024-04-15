package com.i8ai.training.store.service;

import com.i8ai.training.store.service.data.Balance;

import java.util.Date;
import java.util.List;

public interface BalanceService {

    Balance getNetBalance(Date start, Date end);

    List<Balance> getBalancesPerProduct(Date start, Date end);

    List<Balance> getBalancesPerShop(Date start, Date end);

    List<Balance> getBalancesPerProductPerShop(Date start, Date end);

    Balance getBalanceByProduct(Long productId, Date start, Date end);

    Balance getBalanceByShop(Long shopId, Date start, Date end);

    List<Balance> getBalancesByProductPerShop(Long productId, Date start, Date end);

    List<Balance> getBalancesByShopPerProduct(Long shopId, Date start, Date end);

    Balance getBalanceByProductAndShop(Long productId, Long shopId, Date start, Date end);
}
