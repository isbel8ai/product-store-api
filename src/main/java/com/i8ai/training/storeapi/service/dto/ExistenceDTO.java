package com.i8ai.training.storeapi.service.dto;

import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.domain.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExistenceDTO {
    private Double amount;

    private Product product;

    private Shop shop;
}
