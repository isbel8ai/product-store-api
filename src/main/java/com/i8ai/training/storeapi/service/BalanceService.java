package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.service.dto.BalanceDTO;

import java.util.Date;
import java.util.List;

public interface BalanceService {
    BalanceDTO getNetBalance(Date start, Date end);

    List<BalanceDTO> getAllProductsBalances(Date start, Date end);

    List<BalanceDTO> getAllShopsBalances(Date start, Date end);

    BalanceDTO getProductNetBalance(Date start, Date end, Long productId);

    BalanceDTO getShopNetBalance(Date start, Date end, Long shopId);

    List<BalanceDTO> getProductAllShopsBalances(Date start, Date end, Long productId);

    List<BalanceDTO> getShopAllProductsBalances(Date start, Date end, Long shopId);

    BalanceDTO getProductShopNetBalance(Date start, Date end, Long productId, Long shopId);
}
