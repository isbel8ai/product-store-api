package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Pack;

import java.util.Date;

public record PackDto(
        Long id,
        Long lotId,
        Long shopId,
        Double amount,
        Double currentAmount,
        Date deliveredAt
) {
    public PackDto(Pack pack) {
        this(
                pack.getId(),
                pack.getLot().getId(),
                pack.getShop().getId(),
                pack.getReceivedAmount(),
                pack.getCurrentAmount(),
                pack.getDeliveredAt()
        );
    }
}
