package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.exception.ElementNotFoundException;
import com.i8ai.training.store.exception.InvalidInvoiceStatusTransitionException;
import com.i8ai.training.store.model.Invoice;
import com.i8ai.training.store.model.InvoiceStatus;
import com.i8ai.training.store.repository.InvoiceRepository;
import com.i8ai.training.store.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice() {
        Invoice invoice = Invoice.builder()
                .status(InvoiceStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .sales(new ArrayList<>())
                .build();

        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoice(UUID id) {
        return invoiceRepository.findById(id).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public void updateInvoice(UUID id, InvoiceStatus status) {
        invoiceRepository.findById(id).ifPresentOrElse(
                invoice -> {
                    if (invoice.getStatus().notValidTransitionTo(status)) {
                        throw new InvalidInvoiceStatusTransitionException(invoice.getStatus(), status);
                    }
                    invoice.setStatus(status);
                    invoice.setUpdatedAt(LocalDateTime.now());
                    invoiceRepository.save(invoice);
                },
                () -> {
                    throw new ElementNotFoundException();
                }
        );
    }
}
