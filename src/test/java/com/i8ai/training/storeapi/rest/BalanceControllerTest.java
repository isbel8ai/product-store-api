package com.i8ai.training.storeapi.rest;

import com.i8ai.training.storeapi.util.TestUtils;
import com.i8ai.training.storeapi.service.BalanceService;
import com.i8ai.training.storeapi.service.data.Balance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BalanceController.class)
class BalanceControllerTest {

    @MockBean
    BalanceService balanceService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getNetBalance() throws Exception {
        when(balanceService.getNetBalance(null, null)).thenReturn(
                new Balance(TestUtils.NET_SALES_EXPENSES, TestUtils.NET_SALES_INCOME, null, null)
        );

        mockMvc.perform(get("/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spent").value(TestUtils.NET_SALES_EXPENSES))
                .andExpect(jsonPath("$.income").value(TestUtils.NET_SALES_INCOME));
    }

    @Test
    void getBalancesPerProduct() throws Exception {
        when(balanceService.getBalancesPerProduct(null, null)).thenReturn(List.of(
                new Balance(
                        TestUtils.PRODUCT_A_EXPENSES,
                        TestUtils.PRODUCT_B_EXPENSES,
                        TestUtils.PRODUCT_A,
                        null
                ),
                new Balance(
                        TestUtils.PRODUCT_A_INCOME,
                        TestUtils.PRODUCT_B_INCOME,
                        TestUtils.PRODUCT_B,
                        null
                )
        ));

        mockMvc.perform(get("/balance/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.code").value(TestUtils.PRODUCT_A_CODE))
                .andExpect(jsonPath("$[0].spent").value(TestUtils.PRODUCT_A_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(TestUtils.PRODUCT_B_EXPENSES))
                .andExpect(jsonPath("$[1].product.code").value(TestUtils.PRODUCT_B_CODE))
                .andExpect(jsonPath("$[1].spent").value(TestUtils.PRODUCT_A_INCOME))
                .andExpect(jsonPath("$[1].income").value(TestUtils.PRODUCT_B_INCOME));
    }

    @Test
    void getBalanceByProduct() throws Exception {
        when(balanceService.getBalanceByProduct(TestUtils.PRODUCT_A.getId(), null, null)
        ).thenReturn(new Balance(
                TestUtils.PRODUCT_A_EXPENSES,
                TestUtils.PRODUCT_B_EXPENSES,
                TestUtils.PRODUCT_A,
                null
        ));

        mockMvc.perform(get("/balance/product/" + TestUtils.PRODUCT_A.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.code").value(TestUtils.PRODUCT_A_CODE))
                .andExpect(jsonPath("$.spent").value(TestUtils.PRODUCT_A_EXPENSES))
                .andExpect(jsonPath("$.income").value(TestUtils.PRODUCT_B_EXPENSES));
    }

    @Test
    void getBalancesByProductPerShop() throws Exception {
        when(balanceService.getBalancesByProductPerShop(TestUtils.PRODUCT_B_ID, null, null))
                .thenReturn(List.of(
                        new Balance(
                                TestUtils.PACK1B_SALES_EXPENSES,
                                TestUtils.PACK1B_SALES_INCOME,
                                TestUtils.PRODUCT_B,
                                TestUtils.SHOP1
                        ),
                        new Balance(
                                TestUtils.PACK2B_SALES_EXPENSES,
                                TestUtils.PACK2B_SALES_INCOME,
                                TestUtils.PRODUCT_B,
                                TestUtils.SHOP2
                        )
                ));

        mockMvc.perform(get("/balance/product/" + TestUtils.PRODUCT_B_ID + "/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(TestUtils.PRODUCT_B_ID))
                .andExpect(jsonPath("$[0].shop.id").value(TestUtils.SHOP1_ID))
                .andExpect(jsonPath("$[0].spent").value(TestUtils.PACK1B_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(TestUtils.PACK1B_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(TestUtils.PRODUCT_B_ID))
                .andExpect(jsonPath("$[1].shop.id").value(TestUtils.SHOP2_ID))
                .andExpect(jsonPath("$[1].spent").value(TestUtils.PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(TestUtils.PACK2B_SALES_INCOME));
    }

    @Test
    void getBalancesPerShop() throws Exception {
        when(balanceService.getBalancesPerShop(null, null)).thenReturn(List.of(
                        new Balance(
                                TestUtils.SHOP1_EXPENSES,
                                TestUtils.SHOP1_INCOME,
                                null,
                                TestUtils.SHOP1
                        ),
                        new Balance(
                                TestUtils.SHOP2_EXPENSES,
                                TestUtils.SHOP2_INCOME,
                                null,
                                TestUtils.SHOP2
                        )
                )
        );

        mockMvc.perform(get("/balance/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shop.id").value(TestUtils.SHOP1_ID))
                .andExpect(jsonPath("$[0].spent").value(TestUtils.SHOP1_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(TestUtils.SHOP1_INCOME))
                .andExpect(jsonPath("$[1].shop.id").value(TestUtils.SHOP2_ID))
                .andExpect(jsonPath("$[1].spent").value(TestUtils.SHOP2_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(TestUtils.SHOP2_INCOME));
    }

    @Test
    void getBalanceByShop() throws Exception {
        when(balanceService.getBalanceByShop(TestUtils.SHOP1_ID, null, null))
                .thenReturn(new Balance(
                        TestUtils.SHOP1_EXPENSES,
                        TestUtils.SHOP1_INCOME,
                        null,
                        TestUtils.SHOP1
                ));

        mockMvc.perform(get("/balance/shop/" + TestUtils.SHOP1_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shop.id").value(TestUtils.SHOP1_ID))
                .andExpect(jsonPath("$.spent").value(TestUtils.SHOP1_EXPENSES))
                .andExpect(jsonPath("$.income").value(TestUtils.SHOP1_INCOME));
    }

    @Test
    void getBalancesByShopPerProduct() throws Exception {
        when(balanceService.getBalancesByShopPerProduct(TestUtils.SHOP2_ID, null, null)).thenReturn(List.of(
                new Balance(
                        TestUtils.PACK2A_SALES_EXPENSES,
                        TestUtils.PACK2A_SALES_INCOME,
                        TestUtils.PRODUCT_A,
                        TestUtils.SHOP2
                ),
                new Balance(
                        TestUtils.PACK2B_SALES_EXPENSES,
                        TestUtils.PACK2B_SALES_INCOME,
                        TestUtils.PRODUCT_B,
                        TestUtils.SHOP2
                )
        ));

        mockMvc.perform(get("/balance/shop/" + TestUtils.SHOP2_ID + "/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(TestUtils.PRODUCT_A_ID))
                .andExpect(jsonPath("$[0].shop.id").value(TestUtils.SHOP2_ID))
                .andExpect(jsonPath("$[0].spent").value(TestUtils.PACK2A_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(TestUtils.PACK2A_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(TestUtils.PRODUCT_B_ID))
                .andExpect(jsonPath("$[1].shop.id").value(TestUtils.SHOP2_ID))
                .andExpect(jsonPath("$[1].spent").value(TestUtils.PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(TestUtils.PACK2B_SALES_INCOME));
    }

    @Test
    void getBalanceByProductAndShop() throws Exception {
        when(balanceService.getBalanceByProductAndShop(TestUtils.PRODUCT_B_ID, TestUtils.SHOP2_ID, null, null))
                .thenReturn(new Balance(
                        TestUtils.PACK2B_SALES_EXPENSES,
                        TestUtils.PACK2B_SALES_INCOME,
                        TestUtils.PRODUCT_B,
                        TestUtils.SHOP2
                ));

        mockMvc.perform(get("/balance/product/" + TestUtils.PRODUCT_B_ID + "/shop/" + TestUtils.SHOP2_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.code").value(TestUtils.PRODUCT_B_CODE))
                .andExpect(jsonPath("$.shop.id").value(TestUtils.SHOP2_ID))
                .andExpect(jsonPath("$.spent").value(TestUtils.PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$.income").value(TestUtils.PACK2B_SALES_INCOME));
    }

    @Test
    void getBalancesPerProductPerShop() throws Exception {
        when(balanceService.getBalancesPerProductPerShop(null, null))
                .thenReturn(List.of(
                        new Balance(
                                TestUtils.PACK1A_SALES_EXPENSES,
                                TestUtils.PACK1A_SALES_INCOME,
                                TestUtils.PRODUCT_A,
                                TestUtils.SHOP1
                        ),
                        new Balance(
                                TestUtils.PACK1B_SALES_EXPENSES,
                                TestUtils.PACK1B_SALES_INCOME,
                                TestUtils.PRODUCT_B,
                                TestUtils.SHOP1
                        ),
                        new Balance(
                                TestUtils.PACK2A_SALES_EXPENSES,
                                TestUtils.PACK2A_SALES_INCOME,
                                TestUtils.PRODUCT_A,
                                TestUtils.SHOP2
                        ),
                        new Balance(
                                TestUtils.PACK2B_SALES_EXPENSES,
                                TestUtils.PACK2B_SALES_INCOME,
                                TestUtils.PRODUCT_B,
                                TestUtils.SHOP2
                        )
                ));

        mockMvc.perform(get("/balance/product/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(TestUtils.PRODUCT_A_ID))
                .andExpect(jsonPath("$[0].shop.id").value(TestUtils.SHOP1_ID))
                .andExpect(jsonPath("$[0].spent").value(TestUtils.PACK1A_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(TestUtils.PACK1A_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(TestUtils.PRODUCT_B_ID))
                .andExpect(jsonPath("$[1].shop.id").value(TestUtils.SHOP1_ID))
                .andExpect(jsonPath("$[1].spent").value(TestUtils.PACK1B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(TestUtils.PACK1B_SALES_INCOME))
                .andExpect(jsonPath("$[2].product.id").value(TestUtils.PRODUCT_A_ID))
                .andExpect(jsonPath("$[2].shop.id").value(TestUtils.SHOP2_ID))
                .andExpect(jsonPath("$[2].spent").value(TestUtils.PACK2A_SALES_EXPENSES))
                .andExpect(jsonPath("$[2].income").value(TestUtils.PACK2A_SALES_INCOME))
                .andExpect(jsonPath("$[3].product.id").value(TestUtils.PRODUCT_B_ID))
                .andExpect(jsonPath("$[3].shop.id").value(TestUtils.SHOP2_ID))
                .andExpect(jsonPath("$[3].spent").value(TestUtils.PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[3].income").value(TestUtils.PACK2B_SALES_INCOME));
    }
}