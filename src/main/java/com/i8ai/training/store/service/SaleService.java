package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.rest.dto.SaleDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {

    Sale registerSale(SaleDto saleData);

    List<Sale> getSales(LocalDateTime start, LocalDateTime end, Long productId, Long shopId);

    Double getSoldAmountByProductAndShop(Long productId, Long shopId);

    Double getNetSalesIncome(LocalDateTime start, LocalDateTime end);

    Double getSalesIncomeByProduct(Long productId, LocalDateTime start, LocalDateTime end);

    Double getSalesIncomeByShop(Long shopId, LocalDateTime start, LocalDateTime end);

    Double getSalesIncomeByProductAndShop(Long productId, Long shopId, LocalDateTime start, LocalDateTime end);

    Double getNetSalesExpenses(LocalDateTime start, LocalDateTime end);

    Double getSalesExpensesByProduct(Long productId, LocalDateTime start, LocalDateTime end);

    Double getSalesExpensesByShop(Long shopId, LocalDateTime start, LocalDateTime end);

    Double getSalesExpensesByProductAndShop(Long productId, Long shopId, LocalDateTime start, LocalDateTime end);

    void deleteSale(Long saleId);
}
