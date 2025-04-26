package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.exception.ElementNotFoundException;
import com.i8ai.training.store.exception.InvalidInvoiceStatusTransitionException;
import com.i8ai.training.store.model.Invoice;
import com.i8ai.training.store.model.InvoiceStatus;
import com.i8ai.training.store.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.i8ai.training.store.model.InvoiceStatus.OPEN;
import static com.i8ai.training.store.model.InvoiceStatus.PAID;
import static com.i8ai.training.store.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepositoryMock;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Test
    void createInvoice() {
        invoiceService.createInvoice();

        verify(invoiceRepositoryMock).save(any());
    }

    @Test
    void getNotExistingInvoice() {
        when(invoiceRepositoryMock.findById(INVOICE_SECOND_UUID)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> invoiceService.getInvoice(INVOICE_SECOND_UUID));
    }

    @Test
    void getInvoice() {
        when(invoiceRepositoryMock.findById(INVOICE_FIRST_UUID)).thenReturn(Optional.of(INVOICE_FIRST));

        Invoice invoice = invoiceService.getInvoice(INVOICE_FIRST_UUID);

        assertNotNull(invoice);
    }

    @Test
    void updateNotExistingInvoice() {
        when(invoiceRepositoryMock.findById(INVOICE_SECOND_UUID)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> invoiceService.updateInvoice(INVOICE_SECOND_UUID, OPEN));
    }

    @Test
    void updateInvoiceWithInvalidStatus() {
        Invoice invoice = Invoice.builder()
                .id(INVOICE_FIRST_UUID)
                .status(InvoiceStatus.CREATED)
                .sales(new ArrayList<>())
                .build();
        when(invoiceRepositoryMock.findById(INVOICE_FIRST_UUID)).thenReturn(Optional.of(invoice));

        assertThrows(InvalidInvoiceStatusTransitionException.class,
                () -> invoiceService.updateInvoice(INVOICE_FIRST_UUID, PAID));
    }

    @Test
    void updateInvoice() {
        Invoice invoice = Invoice.builder()
                .id(INVOICE_FIRST_UUID)
                .status(InvoiceStatus.CREATED)
                .sales(new ArrayList<>())
                .build();
        when(invoiceRepositoryMock.findById(INVOICE_FIRST_UUID)).thenReturn(Optional.of(invoice));

        invoiceService.updateInvoice(INVOICE_FIRST_UUID, OPEN);

        verify(invoiceRepositoryMock).save(argThat(arg -> arg.getId().equals(INVOICE_FIRST_UUID)));
    }
}