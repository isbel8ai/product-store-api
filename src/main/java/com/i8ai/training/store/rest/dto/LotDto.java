package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Lot;

import java.util.Date;

public record LotDto(
        Long id,
        Long productId,
        Double acquiredAmount,
        Double costPerUnit,
        Double currentAmount,
        Date receivedAt
) {
    public LotDto(Lot lot) {
        this(
                lot.getId(),
                lot.getProduct().getId(),
                lot.getAcquiredAmount(),
                lot.getCostPerUnit(),
                lot.getCurrentAmount(),
                lot.getReceivedAt()
        );
    }
}
