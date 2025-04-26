package com.i8ai.training.store.model;

import lombok.Getter;

import java.util.Set;

@Getter
public enum InvoiceStatus {
    CREATED,
    OPEN,
    PAID,
    CANCELLED,
    EXPIRED;

    public boolean notValidTransitionTo(InvoiceStatus status) {
        return switch (this) {
            case CREATED -> !Set.of(OPEN, CANCELLED, EXPIRED).contains(status);
            case OPEN -> false;
            default -> true;
        };
    }
}
