package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InvoiceRepository extends CrudRepository<Invoice, UUID> {
}
