package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.util.DateTimeUtils;

import java.time.ZonedDateTime;

public record PackDto(
        Long id,
        Long lotId,
        Long shopId,
        Double amount,
        Double currentAmount,
        ZonedDateTime receivedAt
) {
    public PackDto(Pack pack) {
        this(
                pack.getId(),
                pack.getLot().getId(),
                pack.getShop().getId(),
                pack.getReceivedAmount(),
                pack.getCurrentAmount(),
                DateTimeUtils.toUtcDateTime(pack.getReceivedAt())
        );
    }
}
