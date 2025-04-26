package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.util.DateTimeUtils;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record OfferDto(
        Long id,
        @NotNull Long shopId,
        @NotNull Long productId,
        @NotNull Double price,
        Double discount,
        ZonedDateTime createdAt
) {
    public OfferDto(Offer offer) {
        this(
                offer.getId(),
                offer.getPack().getShop().getId(),
                offer.getPack().getLot().getProduct().getId(),
                offer.getPrice(),
                offer.getDiscount(),
                DateTimeUtils.toUtcDateTime(offer.getCreatedAt())
        );
    }
}
