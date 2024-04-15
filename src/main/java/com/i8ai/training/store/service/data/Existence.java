package com.i8ai.training.store.service.data;

import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.model.Shop;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Existence {
    private Double amount;

    private Product product;

    private Shop shop;
}
