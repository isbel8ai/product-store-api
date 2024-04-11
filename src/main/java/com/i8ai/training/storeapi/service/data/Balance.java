package com.i8ai.training.storeapi.service.data;

import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Shop;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Balance {
    Double spent;

    Double income;

    Product product;

    Shop shop;
}
