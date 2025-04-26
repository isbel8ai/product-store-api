package com.i8ai.training.store.exception;

import com.i8ai.training.store.model.InvoiceStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidInvoiceStatusTransitionException extends RuntimeException {
    public InvalidInvoiceStatusTransitionException(InvoiceStatus currentStatus, InvoiceStatus nextStatus) {
        super("Invalid invoice status transition from %s to %s".formatted(currentStatus, nextStatus));
    }
}
