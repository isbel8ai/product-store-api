package com.i8ai.training.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidSaleShopForInvoiceException extends RuntimeException {
    public InvalidSaleShopForInvoiceException(Long saleShopId, Long invoiceShopId) {
        super("Invalid sale shop with ID %d for invoice shop with ID %d".formatted(saleShopId, invoiceShopId));
    }
}
