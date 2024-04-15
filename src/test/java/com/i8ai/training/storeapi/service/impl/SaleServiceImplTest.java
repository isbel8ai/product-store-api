package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.error.NotValidAmountException;
import com.i8ai.training.storeapi.model.Sale;
import com.i8ai.training.storeapi.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @Mock
    private SaleRepository saleRepositoryMock;

    @InjectMocks
    private SaleServiceImpl saleService;

    @Test
    void getSalesWithAllFilters() {
        when(saleRepositoryMock.findAllByRegisteredBetweenAndPackLotProductIdAndPackShopId(
                new Date(35), new Date(40), PRODUCT_A_ID, SHOP1_ID)
        ).thenReturn(List.of(SALE_1A35, SALE_1A40));

        List<Sale> sales = saleService.getSales(new Date(35), new Date(40), PRODUCT_A_ID, SHOP1_ID);

        assertEquals(2, sales.size());
    }

    @Test
    void getSalesWithTimeFilter() {
        when(saleRepositoryMock.findAllByRegisteredBetween(new Date(45), new Date(50)))
                .thenReturn(List.of(SALE_1A35, SALE_1A40));

        List<Sale> sales = saleService.getSales(new Date(45), new Date(50), null, null);

        assertEquals(2, sales.size());
    }

    @Test
    void getSalesWithShopFilter() {
        when(saleRepositoryMock.findAllByRegisteredBetweenAndPackShopId(any(), any(), eq(SHOP2_ID)))
                .thenReturn(List.of(SALE_2A55, SALE_2A60, SALE_2B65, SALE_2B70));

        List<Sale> sales = saleService.getSales(null, null, null, SHOP2_ID);

        assertEquals(4, sales.size());
    }

    @Test
    void getSalesWithProductFilter() {
        when(saleRepositoryMock.findAllByRegisteredBetweenAndPackLotProductId(any(), any(), eq(PRODUCT_B_ID)))
                .thenReturn(List.of(SALE_1B45, SALE_1B50, SALE_2B65, SALE_2B70));

        List<Sale> sales = saleService.getSales(null, null, PRODUCT_B_ID, null);

        assertEquals(4, sales.size());
    }

    @Test
    void getAllSales() {
        when(saleRepositoryMock.findAllByRegisteredBetween(any(), any())).thenReturn(
                List.of(SALE_1A35, SALE_1A40, SALE_1B45, SALE_1B50, SALE_2A55, SALE_2A60, SALE_2B65, SALE_2B70)
        );

        List<Sale> sales = saleService.getSales(null, null, null, null);

        assertEquals(8, sales.size());
    }

    @Test
    void registerSaleWithNotValidAmount() {
        when(saleRepositoryMock.getSoldAmountByPackId(PACK1A_ID)).thenReturn(0.0);

        Sale sale = new Sale(null, new Date(20), -10.0, 8.0, PACK1A);

        assertThrows(NotValidAmountException.class, () -> saleService.registerSale(sale));
    }

    @Test
    void registerSaleWithNotAvailableAmount() {
        when(saleRepositoryMock.getSoldAmountByPackId(PACK1A_ID)).thenReturn(PACK1A_AMOUNT);

        assertThrows(NotValidAmountException.class, () -> saleService.registerSale(SALE_1A40));
    }

    @Test
    void registerSale() {
        when(saleRepositoryMock.getSoldAmountByPackId(PACK1B_ID)).thenReturn(0.0);

        assertDoesNotThrow(() -> saleService.registerSale(SALE_1B45));
    }

    @Test
    void deleteSale() {
        assertDoesNotThrow(() -> saleService.deleteSale(SALE_1A35.getId()));
    }

    @Test
    void getSoldAmountByProductAndShop() {
        when(saleRepositoryMock.getSoldAmountByProductIdAndShopId(PRODUCT_B_ID, SHOP2_ID))
                .thenReturn(PACK2B_SALES_AMOUNT);

        Double amount = saleService.getSoldAmountByProductAndShop(PRODUCT_B_ID, SHOP2_ID);

        assertEquals(PACK2B_SALES_AMOUNT, amount);
    }


    @Test
    void getNetSalesIncome() {
        when(saleRepositoryMock.getNetSalesIncome(any(), any())).thenReturn(NET_SALES_INCOME)
                .thenReturn(NET_SALES_INCOME);

        Double income = saleService.getNetSalesIncome(null, null);

        assertEquals(NET_SALES_INCOME, income);
    }

    @Test
    void getSalesIncomeByProduct() {
        when(saleRepositoryMock.getIncomeByProductId(any(), any(), eq(PRODUCT_A_ID))).thenReturn(PRODUCT_A_INCOME);

        Double income = saleService.getSalesIncomeByProduct(PRODUCT_A_ID, null, null);

        assertEquals(PRODUCT_A_INCOME, income);
    }

    @Test
    void getSalesIncomeByShop() {
        when(saleRepositoryMock.getIncomeByShopId(any(), any(), eq(SHOP2_ID))).thenReturn(SHOP2_INCOME);

        Double income = saleService.getSalesIncomeByShop(SHOP2_ID, null, null);

        assertEquals(SHOP2_INCOME, income);
    }

    @Test
    void getSalesIncomeByProductAndShop() {
        when(saleRepositoryMock.getIncomeByProductIdAndShopId(any(), any(), eq(PRODUCT_B_ID), eq(SHOP1_ID)))
                .thenReturn(PACK1B_SALES_INCOME);

        Double income = saleService.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP1_ID, null, null);

        assertEquals(PACK1B_SALES_INCOME, income);
    }

    @Test
    void getNetSalesExpenses() {
        when(saleRepositoryMock.getNetSalesExpenses(any(), any())).thenReturn(NET_SALES_EXPENSES);

        Double expenses = saleService.getNetSalesExpenses(null, null);

        assertEquals(NET_SALES_EXPENSES, expenses);
    }

    @Test
    void getSalesExpensesByProduct() {
        when(saleRepositoryMock.getSaleExpensesByProductId(any(), any(), eq(PRODUCT_A_ID)))
                .thenReturn(PRODUCT_A_EXPENSES);

        Double expenses = saleService.getSalesExpensesByProduct(PRODUCT_A_ID, null, null);

        assertEquals(PRODUCT_A_EXPENSES, expenses);

    }

    @Test
    void getSalesExpensesByShop() {
        when(saleRepositoryMock.getSaleExpensesByShopId(any(), any(), eq(SHOP2_ID))).thenReturn(SHOP2_EXPENSES);

        Double expenses = saleService.getSalesExpensesByShop(SHOP2_ID, null, null);

        assertEquals(SHOP2_EXPENSES, expenses);
    }

    @Test
    void getSalesExpensesByProductAndShop() {
        when(saleRepositoryMock.getSaleExpensesByProductIdAndShopId(any(), any(), eq(PRODUCT_B_ID), eq(SHOP1_ID)))
                .thenReturn(PACK1B_SALES_EXPENSES);

        Double expenses = saleService.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP1_ID, null, null);

        assertEquals(PACK1B_SALES_EXPENSES, expenses);
    }
}
