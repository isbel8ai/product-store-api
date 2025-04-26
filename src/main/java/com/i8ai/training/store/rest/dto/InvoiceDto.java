package com.i8ai.training.store.rest.dto;

import com.i8ai.training.store.model.Invoice;
import com.i8ai.training.store.model.InvoiceStatus;
import com.i8ai.training.store.util.DateTimeUtils;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record InvoiceDto(
        UUID id,
        InvoiceStatus status,
        List<InvoiceSaleDto> sales,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {
    public InvoiceDto(Invoice invoice) {
        this(
                invoice.getId(),
                invoice.getStatus(),
                invoice.getSales().stream().map(InvoiceSaleDto::new).toList(),
                DateTimeUtils.toUtcDateTime(invoice.getCreatedAt()),
                Optional.ofNullable(invoice.getUpdatedAt()).map(DateTimeUtils::toUtcDateTime).orElse(null)
        );
    }
}
