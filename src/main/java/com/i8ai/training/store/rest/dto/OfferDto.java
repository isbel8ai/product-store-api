package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Offer;

import java.util.Date;

public record OfferDto(
        Long id,
        Long shopId,
        Long productId,
        Double price,
        Double discount,
        Date createdAt
) {
    public OfferDto(Offer offer) {
        this(
                offer.getId(),
                offer.getPack().getShop().getId(),
                offer.getPack().getLot().getProduct().getId(),
                offer.getPrice(),
                offer.getDiscount(),
                offer.getCreatedAt()
        );
    }
}
