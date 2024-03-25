package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.error.NotValidAmountException;
import com.i8ai.training.storeapi.model.*;
import com.i8ai.training.storeapi.repository.SaleRepository;
import com.i8ai.training.storeapi.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class SaleServiceImplTest {

    private static final Product PRODUCT_A = new Product(TestUtils.PRODUCT_A_ID, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null);
    private static final Product PRODUCT_B = new Product(PRODUCT_B_ID, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null);

    private static final Shop SHOP1 = new Shop(SHOP1_ID, SHOP1_NAME, SHOP1_ADDRESS, null);
    private static final Shop SHOP2 = new Shop(SHOP2_ID, SHOP2_NAME, SHOP2_ADDRESS, null);

    private static final Lot LOT_A = new Lot(LOT_A_ID, new Date(5), LOT_A_AMOUNT, PRODUCT_A_COST, PRODUCT_A);
    private static final Lot LOT_B = new Lot(LOT_B_ID, new Date(10), LOT_B_AMOUNT, PRODUCT_B_COST, PRODUCT_B);

    private static final Pack PACK1A = new Pack(PACK1A_ID, new Date(15), PACK1A_AMOUNT, LOT_A, SHOP1);
    private static final Pack PACK1B = new Pack(PACK1B_ID, new Date(20), PACK1B_AMOUNT, LOT_B, SHOP1);
    private static final Pack PACK2A = new Pack(PACK2A_ID, new Date(25), PACK2A_AMOUNT, LOT_A, SHOP2);
    private static final Pack PACK2B = new Pack(PACK2B_ID, new Date(30), PACK2B_AMOUNT, LOT_B, SHOP2);

    private static final Sale SALE_1A35 = new Sale(0x1A35L, new Date(35), SALE_1A35_AMOUNT, PRODUCT_A_PRICE, PACK1A);
    private static final Sale SALE_1A40 = new Sale(0x1A40L, new Date(40), SALE_1A40_AMOUNT, PRODUCT_A_PRICE, PACK1A);
    private static final Sale SALE_1B45 = new Sale(0x1B45L, new Date(45), SALE_1B45_AMOUNT, PRODUCT_B_PRICE, PACK1B);
    private static final Sale SALE_1B50 = new Sale(0x1B50L, new Date(50), SALE_1B50_AMOUNT, PRODUCT_B_PRICE, PACK1B);
    private static final Sale SALE_2A55 = new Sale(0x2A55L, new Date(55), SALE_2A55_AMOUNT, PRODUCT_A_PRICE, PACK2A);
    private static final Sale SALE_2A60 = new Sale(0x2A60L, new Date(60), SALE_2A60_AMOUNT, PRODUCT_A_PRICE, PACK2A);
    private static final Sale SALE_2B65 = new Sale(0x2B65L, new Date(65), SALE_2B65_AMOUNT, PRODUCT_B_PRICE, PACK2B);
    private static final Sale SALE_2B70 = new Sale(0x2B70L, new Date(70), SALE_2B70_AMOUNT, PRODUCT_B_PRICE, PACK2B);

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
