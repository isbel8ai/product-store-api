package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.util.DateTimeUtils;

import java.time.ZonedDateTime;

public record OfferDto(
        Long id,
        Long shopId,
        Long productId,
        Double price,
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
