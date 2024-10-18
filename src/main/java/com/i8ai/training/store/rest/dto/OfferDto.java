package com.i8ai.training.store.rest.dto;

import java.util.Date;

public record OfferDto(
        Long id,
        Long shopId,
        Long productId,
        Double price,
        Double discount,
        Date created
) {
}
