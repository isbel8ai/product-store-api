package com.i8ai.training.storeapi.rest.dto;

import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExistenceDTO {
    private Double amount;

    private Product product;

    private Shop shop;
}
