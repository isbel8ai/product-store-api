package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.util.DateTimeUtils;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record PackDto(
        Long id,
        @NotNull Long lotId,
        @NotNull Long shopId,
        @NotNull Double amount,
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
