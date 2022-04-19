package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.service.ProductService;
import com.i8ai.training.storeapi.service.SaleService;
import com.i8ai.training.storeapi.service.ShopService;
import com.i8ai.training.storeapi.service.data.Balance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BalanceServiceImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private ShopService shopService;

    @Mock
    private SaleService saleService;

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @Test
    void getNetBalance() {
        when(saleService.getNetSalesExpenses(null, null)).thenReturn(NET_SALES_EXPENSES);
        when(saleService.getNetSalesIncome(null, null)).thenReturn(NET_SALES_INCOME);

        Balance balance = balanceService.getNetBalance(null, null);

        assertEquals(NET_SALES_EXPENSES, balance.getSpent());
        assertEquals(NET_SALES_INCOME, balance.getIncome());
        assertNull(balance.getProduct());
        assertNull(balance.getShop());
    }

    @Test
    void getBalancesPerProduct() {
        when(saleService.getSalesExpensesByProduct(PRODUCT_A_ID, null, null)).thenReturn(PRODUCT_A_EXPENSES);
        when(saleService.getSalesIncomeByProduct(PRODUCT_A_ID, null, null)).thenReturn(PRODUCT_A_INCOME);
        when(saleService.getSalesExpensesByProduct(PRODUCT_B_ID, null, null)).thenReturn(PRODUCT_B_EXPENSES);
        when(saleService.getSalesIncomeByProduct(PRODUCT_B_ID, null, null)).thenReturn(PRODUCT_B_INCOME);
        when(productService.getAllProducts()).thenReturn(List.of(PRODUCT_A, PRODUCT_B));

        List<Balance> balances = balanceService.getBalancesPerProduct(null, null);

        assertEquals(2, balances.size());
        assertEquals(PRODUCT_A_EXPENSES, balances.get(0).getSpent());
        assertEquals(PRODUCT_A_INCOME, balances.get(0).getIncome());
        assertEquals(PRODUCT_A, balances.get(0).getProduct());
        assertNull(balances.get(0).getShop());
        assertEquals(PRODUCT_B_EXPENSES, balances.get(1).getSpent());
        assertEquals(PRODUCT_B_INCOME, balances.get(1).getIncome());
        assertEquals(PRODUCT_B, balances.get(1).getProduct());
        assertNull(balances.get(1).getShop());
    }

    @Test
    void getBalancesPerShop() {
        when(saleService.getSalesExpensesByShop(SHOP1_ID, null, null)).thenReturn(SHOP1_EXPENSES);
        when(saleService.getSalesIncomeByShop(SHOP1_ID, null, null)).thenReturn(SHOP1_INCOME);
        when(saleService.getSalesExpensesByShop(SHOP2_ID, null, null)).thenReturn(SHOP2_EXPENSES);
        when(saleService.getSalesIncomeByShop(SHOP2_ID, null, null)).thenReturn(SHOP2_INCOME);
        when(shopService.getAllShops()).thenReturn(List.of(SHOP1, SHOP2));

        List<Balance> balances = balanceService.getBalancesPerShop(null, null);

        assertEquals(2, balances.size());
        assertEquals(SHOP1_EXPENSES, balances.get(0).getSpent());
        assertEquals(SHOP1_INCOME, balances.get(0).getIncome());
        assertNull(balances.get(0).getProduct());
        assertEquals(SHOP1, balances.get(0).getShop());
        assertEquals(SHOP2_EXPENSES, balances.get(1).getSpent());
        assertEquals(SHOP2_INCOME, balances.get(1).getIncome());
        assertNull(balances.get(1).getProduct());
        assertEquals(SHOP2, balances.get(1).getShop());
    }

    @Test
    void getBalanceByProduct() {
        when(saleService.getSalesExpensesByProduct(PRODUCT_A_ID, null, null)).thenReturn(PRODUCT_A_EXPENSES);
        when(saleService.getSalesIncomeByProduct(PRODUCT_A_ID, null, null)).thenReturn(PRODUCT_A_INCOME);
        when(productService.getProduct(PRODUCT_A_ID)).thenReturn(PRODUCT_A);

        Balance balance = balanceService.getBalanceByProduct(PRODUCT_A_ID, null, null);

        assertEquals(PRODUCT_A_EXPENSES, balance.getSpent());
        assertEquals(PRODUCT_A_INCOME, balance.getIncome());
        assertEquals(PRODUCT_A, balance.getProduct());
        assertNull(balance.getShop());
    }

    @Test
    void getBalanceByShop() {
        when(saleService.getSalesExpensesByShop(SHOP1_ID, null, null)).thenReturn(SHOP1_EXPENSES);
        when(saleService.getSalesIncomeByShop(SHOP1_ID, null, null)).thenReturn(SHOP1_INCOME);
        when(shopService.getShop(SHOP1_ID)).thenReturn(SHOP1);

        Balance balance = balanceService.getBalanceByShop(SHOP1_ID, null, null);

        assertEquals(SHOP1_EXPENSES, balance.getSpent());
        assertEquals(SHOP1_INCOME, balance.getIncome());
        assertNull(balance.getProduct());
        assertEquals(SHOP1, balance.getShop());
    }

    @Test
    void getBalancesByProductPerShop() {
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1A_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1A_SALES_INCOME);
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP2_ID, null, null))
                .thenReturn(PACK2A_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP2_ID, null, null))
                .thenReturn(PACK2A_SALES_INCOME);
        when(productService.getProduct(PRODUCT_A_ID)).thenReturn(PRODUCT_A);
        when(shopService.getAllShops()).thenReturn(List.of(SHOP1, SHOP2));

        List<Balance> balances = balanceService.getBalancesByProductPerShop(PRODUCT_A_ID, null, null);

        assertEquals(2, balances.size());
        assertEquals(PACK1A_SALES_EXPENSES, balances.get(0).getSpent());
        assertEquals(PACK1A_SALES_INCOME, balances.get(0).getIncome());
        assertEquals(PRODUCT_A, balances.get(0).getProduct());
        assertEquals(SHOP1, balances.get(0).getShop());
        assertEquals(PACK2A_SALES_EXPENSES, balances.get(1).getSpent());
        assertEquals(PACK2A_SALES_INCOME, balances.get(1).getIncome());
        assertEquals(PRODUCT_A, balances.get(1).getProduct());
        assertEquals(SHOP2, balances.get(1).getShop());
    }

    @Test
    void getBalancesByShopPerProduct() {
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1A_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1A_SALES_INCOME);
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1B_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1B_SALES_INCOME);
        when(productService.getAllProducts()).thenReturn(List.of(PRODUCT_A, PRODUCT_B));
        when(shopService.getShop(SHOP1_ID)).thenReturn(SHOP1);

        List<Balance> balances = balanceService.getBalancesByShopPerProduct(SHOP1_ID, null, null);

        assertEquals(2, balances.size());
        assertEquals(PACK1A_SALES_EXPENSES, balances.get(0).getSpent());
        assertEquals(PACK1A_SALES_INCOME, balances.get(0).getIncome());
        assertEquals(PRODUCT_A, balances.get(0).getProduct());
        assertEquals(SHOP1, balances.get(0).getShop());
        assertEquals(PACK1B_SALES_EXPENSES, balances.get(1).getSpent());
        assertEquals(PACK1B_SALES_INCOME, balances.get(1).getIncome());
        assertEquals(PRODUCT_B, balances.get(1).getProduct());
        assertEquals(SHOP1, balances.get(1).getShop());
    }

    @Test
    void getBalanceByProductAndShop() {
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP2_ID, null, null))
                .thenReturn(PACK2B_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP2_ID, null, null))
                .thenReturn(PACK2B_SALES_INCOME);
        when(productService.getProduct(PRODUCT_B_ID)).thenReturn(PRODUCT_B);
        when(shopService.getShop(SHOP2_ID)).thenReturn(SHOP2);

        Balance balance = balanceService.getBalanceByProductAndShop(
                PRODUCT_B.getId(),
                SHOP2.getId(),
                null,
                null);

        assertEquals(PACK2B_SALES_EXPENSES, balance.getSpent());
        assertEquals(PACK2B_SALES_INCOME, balance.getIncome());
        assertEquals(PRODUCT_B, balance.getProduct());
        assertEquals(SHOP2, balance.getShop());
    }

    @Test
    void getBalancesPerProductPerShop() {
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1A_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1A_SALES_INCOME);
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1B_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP1_ID, null, null))
                .thenReturn(PACK1B_SALES_INCOME);
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_A_ID, SHOP2_ID, null, null))
                .thenReturn(PACK2A_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_A_ID, SHOP2_ID, null, null))
                .thenReturn(PACK2A_SALES_INCOME);
        when(saleService.getSalesExpensesByProductAndShop(PRODUCT_B_ID, SHOP2_ID, null, null))
                .thenReturn(PACK2B_SALES_EXPENSES);
        when(saleService.getSalesIncomeByProductAndShop(PRODUCT_B_ID, SHOP2_ID, null, null))
                .thenReturn(PACK2B_SALES_INCOME);
        when(productService.getAllProducts()).thenReturn(List.of(PRODUCT_A, PRODUCT_B));
        when(shopService.getAllShops()).thenReturn(List.of(SHOP1, SHOP2));

        List<Balance> balances = balanceService.getBalancesPerProductPerShop(null, null);

        assertEquals(4, balances.size());
        assertEquals(PACK1A_SALES_EXPENSES, balances.get(0).getSpent());
        assertEquals(PACK1A_SALES_INCOME, balances.get(0).getIncome());
        assertEquals(PRODUCT_A, balances.get(0).getProduct());
        assertEquals(SHOP1, balances.get(0).getShop());
        assertEquals(PACK1B_SALES_EXPENSES, balances.get(1).getSpent());
        assertEquals(PACK1B_SALES_INCOME, balances.get(1).getIncome());
        assertEquals(PRODUCT_B, balances.get(1).getProduct());
        assertEquals(SHOP1, balances.get(1).getShop());
        assertEquals(PACK2A_SALES_EXPENSES, balances.get(2).getSpent());
        assertEquals(PACK2A_SALES_INCOME, balances.get(2).getIncome());
        assertEquals(PRODUCT_A, balances.get(2).getProduct());
        assertEquals(SHOP2, balances.get(2).getShop());
        assertEquals(PACK2B_SALES_EXPENSES, balances.get(3).getSpent());
        assertEquals(PACK2B_SALES_INCOME, balances.get(3).getIncome());
        assertEquals(PRODUCT_B, balances.get(3).getProduct());
        assertEquals(SHOP2, balances.get(3).getShop());
    }
}
