package com.i8ai.training.store.exception;

import com.i8ai.training.store.model.Pack;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class InvalidSaleAmountException extends RuntimeException {
    public InvalidSaleAmountException(Double amount, Pack pack) {
        super("Invalid sale amount %f for pack with ID %d with current amount %f"
                .formatted(amount, pack.getId(), pack.getCurrentAmount()));
    }
}
