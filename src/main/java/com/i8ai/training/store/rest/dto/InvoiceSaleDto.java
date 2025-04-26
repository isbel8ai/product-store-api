package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Sale;

public record InvoiceSaleDto(
        String productName,
        String productDescription,
        String measureUnit,
        Double price,
        Double discount,
        Double amount
) {
    public InvoiceSaleDto(Sale sale) {
        this(
                sale.getOffer().getPack().getLot().getProduct().getName(),
                sale.getOffer().getPack().getLot().getProduct().getDescription(),
                sale.getOffer().getPack().getLot().getProduct().getMeasureUnit(),
                sale.getOffer().getPrice(),
                sale.getOffer().getDiscount(),
                sale.getAmount()
        );
    }
}
