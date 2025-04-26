package com.i8ai.training.store.rest;

import com.i8ai.training.store.rest.dto.InvoiceDto;
import com.i8ai.training.store.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceDto createInvoice() {
        return new InvoiceDto(invoiceService.createInvoice());
    }

    @GetMapping("{invoiceId}")
    public InvoiceDto getInvoice(@PathVariable("invoiceId") UUID invoiceId) {
        return new InvoiceDto(invoiceService.getInvoice(invoiceId));
    }
}
