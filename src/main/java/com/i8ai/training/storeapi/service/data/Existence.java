package com.i8ai.training.storeapi.service.data;

import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Existence {
    private Double amount;

    private Product product;

    private Shop shop;
}
