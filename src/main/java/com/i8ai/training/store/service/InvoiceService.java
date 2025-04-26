package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Invoice;
import com.i8ai.training.store.model.InvoiceStatus;

import java.util.UUID;

public interface InvoiceService {

    Invoice createInvoice();

    Invoice getInvoice(UUID id);

    void updateInvoice(UUID id, InvoiceStatus status);
}
