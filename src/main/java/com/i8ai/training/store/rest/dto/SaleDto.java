package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Sale;

import java.util.Date;

public record SaleDto(
        Long id,
        Long offerId,
        Double amount,
        Date registeredAt
) {
    public SaleDto(Sale sale) {
        this(
                sale.getId(),
                sale.getOffer().getId(),
                sale.getAmount(),
                sale.getRegisteredAt()
        );
    }
}
