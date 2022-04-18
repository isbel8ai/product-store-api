package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.service.data.Balance;

import java.util.Date;
import java.util.List;

public interface BalanceService {
    Balance getNetBalance(Date start, Date end);

    List<Balance> getBalancesPerProduct(Date start, Date end);

    List<Balance> getBalancesPerShop(Date start, Date end);

    List<Balance> getBalancesPerProductPerShop(Date start, Date end);

    Balance getBalanceByProduct(Date start, Date end, Long productId);

    Balance getBalanceByShop(Date start, Date end, Long shopId);

    List<Balance> getBalancesByProductPerShop(Date start, Date end, Long productId);

    List<Balance> getBalancesByShopPerProduct(Date start, Date end, Long shopId);

    Balance getBalanceByProductAndShop(Date start, Date end, Long productId, Long shopId);
}
