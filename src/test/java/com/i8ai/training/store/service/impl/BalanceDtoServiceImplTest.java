package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.rest.dto.BalanceDto;
import com.i8ai.training.store.service.ProductService;
import com.i8ai.training.store.service.SaleService;
import com.i8ai.training.store.service.ShopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.i8ai.training.store.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceDtoServiceImplTest {

    @Mock
    private ProductService productServiceMock;

    @Mock
    private ShopService shopServiceMock;

    @Mock
    private SaleService saleServiceMock;

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @Test
    void getNetBalance() {
        when(saleServiceMock.getNetSalesExpenses(null, null)).thenReturn(NET_SALES_EXPENSES);
        when(saleServiceMock.getNetSalesIncome(null, null)).thenReturn(NET_SALES_INCOME);

        BalanceDto balanceDto = balanceService.getNetBalance(null, null);

        assertEquals(NET_SALES_EXPENSES, balanceDto.getSpent());
        assertEquals(NET_SALES_INCOME, balanceDto.getIncome());
        assertNull(balanceDto.getProduct());
        assertNull(balanceDto.getShop());
    }

    @Test
    void getBalancesPerProduct() {
        when(productServiceMock.getAllProducts()).thenReturn(List.of(PRODUCT_A, PRODUCT_B));
        when(saleServiceMock.getSalesExpensesByProduct(PRODUCT_A_ID, null, null)).thenReturn(PRODUCT_A_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProduct(PRODUCT_A_ID, null, null)).thenReturn(PRODUCT_A_INCOME);
        when(saleServiceMock.getSalesExpensesByProduct(PRODUCT_B_ID, null, null)).thenReturn(PRODUCT_B_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProduct(PRODUCT_B_ID, null, null)).thenReturn(PRODUCT_B_INCOME);

        List<BalanceDto> balanceDtos = balanceService.getBalancesPerProduct(null, null);

        assertEquals(2, balanceDtos.size());
        assertEquals(PRODUCT_A_EXPENSES, balanceDtos.get(0).getSpent());
        assertEquals(PRODUCT_A_INCOME, balanceDtos.get(0).getIncome());
        assertEquals(PRODUCT_A, balanceDtos.get(0).getProduct());
        assertNull(balanceDtos.get(0).getShop());
        assertEquals(PRODUCT_B_EXPENSES, balanceDtos.get(1).getSpent());
        assertEquals(PRODUCT_B_INCOME, balanceDtos.get(1).getIncome());
        assertEquals(PRODUCT_B, balanceDtos.get(1).getProduct());
        assertNull(balanceDtos.get(1).getShop());
    }

    @Test
    void getBalancesPerShop() {
        when(shopServiceMock.getAllShops()).thenReturn(List.of(SHOP1, SHOP2));
        when(saleServiceMock.getSalesExpensesByShop(SHOP1.getId(), null, null)).thenReturn(SHOP1_EXPENSES);
        when(saleServiceMock.getSalesIncomeByShop(SHOP1.getId(), null, null)).thenReturn(SHOP1_INCOME);
        when(saleServiceMock.getSalesExpensesByShop(SHOP2.getId(), null, null)).thenReturn(SHOP2_EXPENSES);
        when(saleServiceMock.getSalesIncomeByShop(SHOP2.getId(), null, null)).thenReturn(SHOP2_INCOME);

        List<BalanceDto> balanceDtos = balanceService.getBalancesPerShop(null, null);

        assertEquals(2, balanceDtos.size());
        assertEquals(SHOP1_EXPENSES, balanceDtos.get(0).getSpent());
        assertEquals(SHOP1_INCOME, balanceDtos.get(0).getIncome());
        assertNull(balanceDtos.get(0).getProduct());
        assertEquals(SHOP1, balanceDtos.get(0).getShop());
        assertEquals(SHOP2_EXPENSES, balanceDtos.get(1).getSpent());
        assertEquals(SHOP2_INCOME, balanceDtos.get(1).getIncome());
        assertNull(balanceDtos.get(1).getProduct());
        assertEquals(SHOP2, balanceDtos.get(1).getShop());
    }

    @Test
    void getBalanceByProduct() {
        when(saleServiceMock.getSalesExpensesByProduct(PRODUCT_A_ID, null, null)).thenReturn(PRODUCT_A_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProduct(PRODUCT_A_ID, null, null)).thenReturn(PRODUCT_A_INCOME);
        when(productServiceMock.getProduct(PRODUCT_A_ID)).thenReturn(PRODUCT_A);

        BalanceDto balanceDto = balanceService.getBalanceByProduct(PRODUCT_A_ID, null, null);

        assertEquals(PRODUCT_A_EXPENSES, balanceDto.getSpent());
        assertEquals(PRODUCT_A_INCOME, balanceDto.getIncome());
        assertEquals(PRODUCT_A, balanceDto.getProduct());
        assertNull(balanceDto.getShop());
    }

    @Test
    void getBalanceByShop() {
        when(saleServiceMock.getSalesExpensesByShop(SHOP1.getId(), null, null)).thenReturn(SHOP1_EXPENSES);
        when(saleServiceMock.getSalesIncomeByShop(SHOP1.getId(), null, null)).thenReturn(SHOP1_INCOME);
        when(shopServiceMock.getShop(SHOP1.getId())).thenReturn(SHOP1);

        BalanceDto balanceDto = balanceService.getBalanceByShop(SHOP1.getId(), null, null);

        assertEquals(SHOP1_EXPENSES, balanceDto.getSpent());
        assertEquals(SHOP1_INCOME, balanceDto.getIncome());
        assertNull(balanceDto.getProduct());
        assertEquals(SHOP1, balanceDto.getShop());
    }

    @Test
    void getBalancesByProductPerShop() {
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1A_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1A_SALES_INCOME);
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP2.getId(), null, null))
                .thenReturn(PACK2A_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP2.getId(), null, null))
                .thenReturn(PACK2A_SALES_INCOME);
        when(productServiceMock.getProduct(PRODUCT_A_ID)).thenReturn(PRODUCT_A);
        when(shopServiceMock.getAllShops()).thenReturn(List.of(SHOP1, SHOP2));

        List<BalanceDto> balanceDtos = balanceService.getBalancesByProductPerShop(PRODUCT_A_ID, null, null);

        assertEquals(2, balanceDtos.size());
        assertEquals(PACK1A_SALES_EXPENSES, balanceDtos.get(0).getSpent());
        assertEquals(PACK1A_SALES_INCOME, balanceDtos.get(0).getIncome());
        assertEquals(PRODUCT_A, balanceDtos.get(0).getProduct());
        assertEquals(SHOP1, balanceDtos.get(0).getShop());
        assertEquals(PACK2A_SALES_EXPENSES, balanceDtos.get(1).getSpent());
        assertEquals(PACK2A_SALES_INCOME, balanceDtos.get(1).getIncome());
        assertEquals(PRODUCT_A, balanceDtos.get(1).getProduct());
        assertEquals(SHOP2, balanceDtos.get(1).getShop());
    }

    @Test
    void getBalancesByShopPerProduct() {
        when(shopServiceMock.getShop(SHOP1.getId())).thenReturn(SHOP1);
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1A_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1A_SALES_INCOME);
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1B_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1B_SALES_INCOME);
        when(productServiceMock.getAllProducts()).thenReturn(List.of(PRODUCT_A, PRODUCT_B));

        List<BalanceDto> balanceDtos = balanceService.getBalancesByShopPerProduct(SHOP1.getId(), null, null);

        assertEquals(2, balanceDtos.size());
        assertEquals(PACK1A_SALES_EXPENSES, balanceDtos.get(0).getSpent());
        assertEquals(PACK1A_SALES_INCOME, balanceDtos.get(0).getIncome());
        assertEquals(PRODUCT_A, balanceDtos.get(0).getProduct());
        assertEquals(SHOP1, balanceDtos.get(0).getShop());
        assertEquals(PACK1B_SALES_EXPENSES, balanceDtos.get(1).getSpent());
        assertEquals(PACK1B_SALES_INCOME, balanceDtos.get(1).getIncome());
        assertEquals(PRODUCT_B, balanceDtos.get(1).getProduct());
        assertEquals(SHOP1, balanceDtos.get(1).getShop());
    }

    @Test
    void getBalanceByProductAndShop() {
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP2.getId(), null, null))
                .thenReturn(PACK2B_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP2.getId(), null, null))
                .thenReturn(PACK2B_SALES_INCOME);
        when(productServiceMock.getProduct(PRODUCT_B_ID)).thenReturn(PRODUCT_B);
        when(shopServiceMock.getShop(SHOP2.getId())).thenReturn(SHOP2);

        BalanceDto balanceDto = balanceService.getBalanceByProductAndShop(
                PRODUCT_B_ID,
                SHOP2.getId(),
                null,
                null);

        assertEquals(PACK2B_SALES_EXPENSES, balanceDto.getSpent());
        assertEquals(PACK2B_SALES_INCOME, balanceDto.getIncome());
        assertEquals(PRODUCT_B, balanceDto.getProduct());
        assertEquals(SHOP2, balanceDto.getShop());
    }

    @Test
    void getBalancesPerProductPerShop() {
        when(shopServiceMock.getAllShops()).thenReturn(List.of(SHOP1, SHOP2));
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1A_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1A_SALES_INCOME);
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1B_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP1.getId(), null, null))
                .thenReturn(PACK1B_SALES_INCOME);
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP2.getId(), null, null))
                .thenReturn(PACK2A_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP2.getId(), null, null))
                .thenReturn(PACK2A_SALES_INCOME);
        when(saleServiceMock.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP2.getId(), null, null))
                .thenReturn(PACK2B_SALES_EXPENSES);
        when(saleServiceMock.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP2.getId(), null, null))
                .thenReturn(PACK2B_SALES_INCOME);
        when(productServiceMock.getAllProducts()).thenReturn(List.of(PRODUCT_A, PRODUCT_B));

        List<BalanceDto> balanceDtos = balanceService.getBalancesPerProductPerShop(null, null);

        assertEquals(4, balanceDtos.size());
        assertEquals(PACK1A_SALES_EXPENSES, balanceDtos.get(0).getSpent());
        assertEquals(PACK1A_SALES_INCOME, balanceDtos.get(0).getIncome());
        assertEquals(PRODUCT_A, balanceDtos.get(0).getProduct());
        assertEquals(SHOP1, balanceDtos.get(0).getShop());
        assertEquals(PACK1B_SALES_EXPENSES, balanceDtos.get(1).getSpent());
        assertEquals(PACK1B_SALES_INCOME, balanceDtos.get(1).getIncome());
        assertEquals(PRODUCT_B, balanceDtos.get(1).getProduct());
        assertEquals(SHOP1, balanceDtos.get(1).getShop());
        assertEquals(PACK2A_SALES_EXPENSES, balanceDtos.get(2).getSpent());
        assertEquals(PACK2A_SALES_INCOME, balanceDtos.get(2).getIncome());
        assertEquals(PRODUCT_A, balanceDtos.get(2).getProduct());
        assertEquals(SHOP2, balanceDtos.get(2).getShop());
        assertEquals(PACK2B_SALES_EXPENSES, balanceDtos.get(3).getSpent());
        assertEquals(PACK2B_SALES_INCOME, balanceDtos.get(3).getIncome());
        assertEquals(PRODUCT_B, balanceDtos.get(3).getProduct());
        assertEquals(SHOP2, balanceDtos.get(3).getShop());
    }
}
