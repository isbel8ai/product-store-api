package com.i8ai.training.storeapi.rest.dto;

import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Shop;
import com.i8ai.training.storeapi.service.data.Balance;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BalanceResponse {
    Double spent;

    Double income;

    Product product;

    Shop shop;

    public static BalanceResponse fromFilteredBalance(Balance balance) {
        return builder()
                .spent(balance.getSpent())
                .income(balance.getIncome())
                .product(balance.getProduct())
                .shop(balance.getShop())
                .build();
    }
}
