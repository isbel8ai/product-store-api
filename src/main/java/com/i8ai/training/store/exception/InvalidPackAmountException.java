package com.i8ai.training.store.exception;

import com.i8ai.training.store.model.Lot;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidPackAmountException extends RuntimeException {
    public InvalidPackAmountException(Double amount, Lot lot) {
        super("Invalid pack amount %f for lot with ID %d and current amount %f"
                .formatted(amount, lot.getId(), lot.getCurrentAmount()));
    }
}
