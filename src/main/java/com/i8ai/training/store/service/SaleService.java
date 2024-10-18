package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.rest.dto.SaleDto;

import java.util.Date;
import java.util.List;

public interface SaleService {

    Sale registerSale(SaleDto saleData);

    List<Sale> getSales(Date start, Date end, Long productId, Long shopId);

    Double getSoldAmountByProductAndShop(Long productId, Long shopId);

    Double getNetSalesIncome(Date start, Date end);

    Double getSalesIncomeByProduct(Long productId, Date start, Date end);

    Double getSalesIncomeByShop(Long shopId, Date start, Date end);

    Double getSalesIncomeByProductAndShop(Long productId, Long shopId, Date start, Date end);

    Double getNetSalesExpenses(Date start, Date end);

    Double getSalesExpensesByProduct(Long productId, Date start, Date end);

    Double getSalesExpensesByShop(Long shopId, Date start, Date end);

    Double getSalesExpensesByProductAndShop(Long productId, Long shopId, Date start, Date end);
}
