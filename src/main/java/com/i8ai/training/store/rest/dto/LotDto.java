package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.util.DateTimeUtils;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record LotDto(
        Long id,
        @NotNull Long productId,
        @NotNull Double acquiredAmount,
        @NotNull Double costPerUnit,
        Double currentAmount,
        ZonedDateTime acquiredAt
) {
    public LotDto(Lot lot) {
        this(
                lot.getId(),
                lot.getProduct().getId(),
                lot.getAcquiredAmount(),
                lot.getCostPerUnit(),
                lot.getCurrentAmount(),
                DateTimeUtils.toUtcDateTime(lot.getAcquiredAt())
        );
    }
}
