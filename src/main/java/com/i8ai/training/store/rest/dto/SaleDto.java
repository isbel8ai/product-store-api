package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.util.DateTimeUtils;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

public record SaleDto(
        Long id,
        @NotNull UUID invoiceId,
        @NotNull Long offerId,
        @NotNull Double amount,
        ZonedDateTime registeredAt
) {
    public SaleDto(Sale sale) {
        this(
                sale.getId(),
                sale.getInvoice().getId(),
                sale.getOffer().getId(),
                sale.getAmount(),
                DateTimeUtils.toUtcDateTime(sale.getRegisteredAt())
        );
    }
}
