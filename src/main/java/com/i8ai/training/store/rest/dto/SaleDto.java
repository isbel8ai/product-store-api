package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.util.DateTimeUtils;

import java.time.ZonedDateTime;

public record SaleDto(
        Long id,
        Long offerId,
        Double amount,
        ZonedDateTime registeredAt
) {
    public SaleDto(Sale sale) {
        this(
                sale.getId(),
                sale.getOffer().getId(),
                sale.getAmount(),
                DateTimeUtils.toUtcDateTime(sale.getRegisteredAt())
        );
    }
}
