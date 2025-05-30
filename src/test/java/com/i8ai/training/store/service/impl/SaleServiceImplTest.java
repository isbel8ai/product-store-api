package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.exception.InvalidInvoiceStatusTransitionException;
import com.i8ai.training.store.exception.InvalidSaleAmountException;
import com.i8ai.training.store.exception.InvalidSaleShopForInvoiceException;
import com.i8ai.training.store.model.Invoice;
import com.i8ai.training.store.model.InvoiceStatus;
import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.repository.SaleRepository;
import com.i8ai.training.store.rest.dto.SaleDto;
import com.i8ai.training.store.service.InvoiceService;
import com.i8ai.training.store.service.OfferService;
import com.i8ai.training.store.service.PackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.i8ai.training.store.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @Mock
    private InvoiceService invoiceServiceMock;

    @Mock
    private OfferService offerServiceMock;

    @Mock
    private PackService packServiceMock;

    @Mock
    private SaleRepository saleRepositoryMock;

    @InjectMocks
    private SaleServiceImpl saleService;

    @Test
    void getSalesWithAllFilters() {
        when(saleRepositoryMock.findAllByRegisteredAtBetweenAndOfferPackLotProductIdAndOfferPackShopId(
                any(), any(), eq(PRODUCT_A_ID), eq(SHOP1_ID))
        ).thenReturn(List.of(SALE_1A35, SALE_1A40));

        List<Sale> sales = saleService.getSales(LocalDateTime.now(), LocalDateTime.now(), PRODUCT_A_ID, SHOP1_ID);

        assertEquals(2, sales.size());
    }

    @Test
    void getSalesWithTimeFilter() {
        when(saleRepositoryMock.findAllByRegisteredAtBetween(any(), any())).thenReturn(List.of(SALE_1A35, SALE_1A40));

        List<Sale> sales = saleService.getSales(LocalDateTime.now(), LocalDateTime.now(), null, null);
        assertEquals(2, sales.size());
    }

    @Test
    void getSalesWithShopFilter() {
        when(saleRepositoryMock.findAllByRegisteredAtBetweenAndOfferPackShopId(any(), any(), eq(SHOP2_ID)))
                .thenReturn(List.of(SALE_2A55, SALE_2A60, SALE_2B65, SALE_2B70));

        List<Sale> sales = saleService.getSales(null, null, null, SHOP2_ID);

        assertEquals(4, sales.size());
    }

    @Test
    void getSalesWithProductFilter() {
        when(saleRepositoryMock.findAllByRegisteredAtBetweenAndOfferPackLotProductId(any(), any(), eq(PRODUCT_B_ID)))
                .thenReturn(List.of(SALE_1B45, SALE_1B50, SALE_2B65, SALE_2B70));

        List<Sale> sales = saleService.getSales(null, null, PRODUCT_B_ID, null);

        assertEquals(4, sales.size());
    }

    @Test
    void getAllSales() {
        when(saleRepositoryMock.findAllByRegisteredAtBetween(any(), any())).thenReturn(
                List.of(SALE_1A35, SALE_1A40, SALE_1B45, SALE_1B50, SALE_2A55, SALE_2A60, SALE_2B65, SALE_2B70)
        );

        List<Sale> sales = saleService.getSales(null, null, null, null);

        assertEquals(8, sales.size());
    }

    @Test
    void registerSaleWithPaidInvoice() {
        Invoice invoice = Invoice.builder()
                .id(INVOICE_SECOND_UUID)
                .status(InvoiceStatus.PAID)
                .sales(List.of(SALE_2A55))
                .build();
        when(invoiceServiceMock.getInvoice(INVOICE_SECOND_UUID)).thenReturn(invoice);
        SaleDto saleDto = new SaleDto(null, INVOICE_SECOND_UUID, OFFER2A_ID, PACK2A_AMOUNT, null);

        assertThrows(InvalidInvoiceStatusTransitionException.class, () -> saleService.registerSale(saleDto));
    }

    @Test
    void registerSaleWithDistinctShop() {
        Invoice invoice = Invoice.builder()
                .id(INVOICE_FIRST_UUID)
                .status(InvoiceStatus.CREATED)
                .sales(List.of(SALE_1A40))
                .build();
        when(invoiceServiceMock.getInvoice(INVOICE_FIRST_UUID)).thenReturn(invoice);
        when(offerServiceMock.getOffer(OFFER2A_ID)).thenReturn(OFFER2A);
        SaleDto saleDto = new SaleDto(null, INVOICE_FIRST_UUID, OFFER2A_ID, PACK2A_AMOUNT, null);

        assertThrows(InvalidSaleShopForInvoiceException.class, () -> saleService.registerSale(saleDto));
    }

    @Test
    void registerSaleWithNotValidAmount() {
        when(invoiceServiceMock.getInvoice(INVOICE_FIRST_UUID)).thenReturn(INVOICE_FIRST);
        when(offerServiceMock.getOffer(OFFER1A_ID)).thenReturn(OFFER1A);
        SaleDto saleDto = new SaleDto(null, INVOICE_FIRST_UUID, OFFER1A_ID, -10.0, null);

        assertThrows(InvalidSaleAmountException.class, () -> saleService.registerSale(saleDto));
    }

    @Test
    void registerSaleWithNotAvailableAmount() {
        when(invoiceServiceMock.getInvoice(INVOICE_SECOND_UUID)).thenReturn(INVOICE_SECOND);
        when(offerServiceMock.getOffer(OFFER2A_ID)).thenReturn(OFFER1A);
        SaleDto saleDto = new SaleDto(null, INVOICE_SECOND_UUID, OFFER2A_ID, PACK2A_AMOUNT + 1, null);

        assertThrows(InvalidSaleAmountException.class, () -> saleService.registerSale(saleDto));
    }

    @Test
    void registerSale() {
        when(invoiceServiceMock.getInvoice(INVOICE_SECOND_UUID)).thenReturn(INVOICE_SECOND);
        when(offerServiceMock.getOffer(OFFER1A_ID)).thenReturn(OFFER1A);

        SaleDto saleDto = new SaleDto(null, INVOICE_SECOND_UUID, OFFER1A_ID, SALE_1A40_AMOUNT, null);

        assertDoesNotThrow(() -> saleService.registerSale(saleDto));
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
