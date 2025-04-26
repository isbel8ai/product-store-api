package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.model.Shop;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceDto {
    Double spent;
    Double income;
    Product product;
    Shop shop;
}
