package com.i8ai.training.storeapi.rest;

import com.i8ai.training.storeapi.helper.TestHelper;
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
                new Balance(TestHelper.SPENT_AMOUNT, TestHelper.INCOME_AMOUNT, null, null)
        );

        mockMvc.perform(get("/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spent").value(TestHelper.SPENT_AMOUNT))
                .andExpect(jsonPath("$.income").value(TestHelper.INCOME_AMOUNT));
    }

    @Test
    void getBalancesPerProduct() throws Exception {
        when(balanceService.getBalancesPerProduct(null, null)).thenReturn(List.of(
                        new Balance(
                                TestHelper.PRODUCT_A_SPENT_AMOUNT,
                                TestHelper.PRODUCT_A_INCOME_AMOUNT,
                                TestHelper.PRODUCT_A,
                                null
                        ),
                        new Balance(
                                TestHelper.PRODUCT_B_SPENT_AMOUNT,
                                TestHelper.PRODUCT_B_INCOME_AMOUNT,
                                TestHelper.PRODUCT_B,
                                null
                        )
                )
        );

        mockMvc.perform(get("/balance/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.code").value(TestHelper.PRODUCT_A_CODE))
                .andExpect(jsonPath("$[0].spent").value(TestHelper.PRODUCT_A_SPENT_AMOUNT))
                .andExpect(jsonPath("$[0].income").value(TestHelper.PRODUCT_A_INCOME_AMOUNT))
                .andExpect(jsonPath("$[1].product.code").value(TestHelper.PRODUCT_B_CODE))
                .andExpect(jsonPath("$[1].spent").value(TestHelper.PRODUCT_B_SPENT_AMOUNT))
                .andExpect(jsonPath("$[1].income").value(TestHelper.PRODUCT_B_INCOME_AMOUNT));
    }

    @Test
    void getBalanceByProduct() throws Exception {
        when(balanceService.getBalanceByProduct(null, null, TestHelper.PRODUCT_A_ID)).thenReturn(
                new Balance(
                        TestHelper.PRODUCT_A_SPENT_AMOUNT,
                        TestHelper.PRODUCT_A_INCOME_AMOUNT,
                        TestHelper.PRODUCT_A,
                        null
                )
        );

        mockMvc.perform(get("/balance/product/" + TestHelper.PRODUCT_A_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.code").value(TestHelper.PRODUCT_A_CODE))
                .andExpect(jsonPath("$.spent").value(TestHelper.PRODUCT_A_SPENT_AMOUNT))
                .andExpect(jsonPath("$.income").value(TestHelper.PRODUCT_A_INCOME_AMOUNT));
    }

    @Test
    void getBalancesByProductPerShop() throws Exception {
        when(balanceService.getBalancesByProductPerShop(null, null, TestHelper.PRODUCT_B_ID))
                .thenReturn(List.of(
                                new Balance(
                                        TestHelper.SALE1B_SPENT_AMOUNT,
                                        TestHelper.SALE1B_INCOME_AMOUNT,
                                        TestHelper.PRODUCT_B,
                                        TestHelper.SHOP1
                                ),
                                new Balance(
                                        TestHelper.SALE2B_SPENT_AMOUNT,
                                        TestHelper.SALE2B_INCOME_AMOUNT,
                                        TestHelper.PRODUCT_B,
                                        TestHelper.SHOP2
                                )
                        )
                );

        mockMvc.perform(get("/balance/product/" + TestHelper.PRODUCT_B_ID + "/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(TestHelper.PRODUCT_B_ID))
                .andExpect(jsonPath("$[0].shop.id").value(TestHelper.SHOP1_ID))
                .andExpect(jsonPath("$[0].spent").value(TestHelper.SALE1B_SPENT_AMOUNT))
                .andExpect(jsonPath("$[0].income").value(TestHelper.SALE1B_INCOME_AMOUNT))
                .andExpect(jsonPath("$[1].product.id").value(TestHelper.PRODUCT_B_ID))
                .andExpect(jsonPath("$[1].shop.id").value(TestHelper.SHOP2_ID))
                .andExpect(jsonPath("$[1].spent").value(TestHelper.SALE2B_SPENT_AMOUNT))
                .andExpect(jsonPath("$[1].income").value(TestHelper.SALE2B_INCOME_AMOUNT));
    }

    @Test
    void getBalancesPerShop() throws Exception {
        when(balanceService.getBalancesPerShop(null, null)).thenReturn(List.of(
                        new Balance(
                                TestHelper.SHOP1_SPENT_AMOUNT,
                                TestHelper.SHOP1_INCOME_AMOUNT,
                                null,
                                TestHelper.SHOP1
                        ),
                        new Balance(
                                TestHelper.SHOP2_SPENT_AMOUNT,
                                TestHelper.SHOP2_INCOME_AMOUNT,
                                null,
                                TestHelper.SHOP2
                        )
                )
        );

        mockMvc.perform(get("/balance/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shop.id").value(TestHelper.SHOP1_ID))
                .andExpect(jsonPath("$[0].spent").value(TestHelper.SHOP1_SPENT_AMOUNT))
                .andExpect(jsonPath("$[0].income").value(TestHelper.SHOP1_INCOME_AMOUNT))
                .andExpect(jsonPath("$[1].shop.id").value(TestHelper.SHOP2_ID))
                .andExpect(jsonPath("$[1].spent").value(TestHelper.SHOP2_SPENT_AMOUNT))
                .andExpect(jsonPath("$[1].income").value(TestHelper.SHOP2_INCOME_AMOUNT));
    }

    @Test
    void getBalanceByShop() throws Exception {
        when(balanceService.getBalanceByShop(null, null, TestHelper.SHOP1_ID)).thenReturn(
                new Balance(
                        TestHelper.SHOP1_SPENT_AMOUNT,
                        TestHelper.SHOP1_INCOME_AMOUNT,
                        null,
                        TestHelper.SHOP1
                )
        );

        mockMvc.perform(get("/balance/shop/" + TestHelper.SHOP1_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shop.id").value(TestHelper.SHOP1_ID))
                .andExpect(jsonPath("$.spent").value(TestHelper.SHOP1_SPENT_AMOUNT))
                .andExpect(jsonPath("$.income").value(TestHelper.SHOP1_INCOME_AMOUNT));
    }

    @Test
    void getBalancesByShopPerProduct() throws Exception {
        when(balanceService.getBalancesByShopPerProduct(null, null, TestHelper.SHOP2_ID))
                .thenReturn(List.of(
                                new Balance(
                                        TestHelper.SALE2A_SPENT_AMOUNT,
                                        TestHelper.SALE2A_INCOME_AMOUNT,
                                        TestHelper.PRODUCT_A,
                                        TestHelper.SHOP2
                                ),
                                new Balance(
                                        TestHelper.SALE2B_SPENT_AMOUNT,
                                        TestHelper.SALE2B_INCOME_AMOUNT,
                                        TestHelper.PRODUCT_B,
                                        TestHelper.SHOP2
                                )
                        )
                );

        mockMvc.perform(get("/balance/shop/" + TestHelper.SHOP2_ID + "/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(TestHelper.PRODUCT_A_ID))
                .andExpect(jsonPath("$[0].shop.id").value(TestHelper.SHOP2_ID))
                .andExpect(jsonPath("$[0].spent").value(TestHelper.SALE2A_SPENT_AMOUNT))
                .andExpect(jsonPath("$[0].income").value(TestHelper.SALE2A_INCOME_AMOUNT))
                .andExpect(jsonPath("$[1].product.id").value(TestHelper.PRODUCT_B_ID))
                .andExpect(jsonPath("$[1].shop.id").value(TestHelper.SHOP2_ID))
                .andExpect(jsonPath("$[1].spent").value(TestHelper.SALE2B_SPENT_AMOUNT))
                .andExpect(jsonPath("$[1].income").value(TestHelper.SALE2B_INCOME_AMOUNT));
    }

    @Test
    void getBalanceByProductAndShop() throws Exception {
        when(balanceService.getBalanceByProductAndShop(null, null, TestHelper.PRODUCT_B_ID, TestHelper.SHOP2_ID))
                .thenReturn(new Balance(
                                TestHelper.SALE2B_SPENT_AMOUNT,
                                TestHelper.SALE2B_INCOME_AMOUNT,
                                TestHelper.PRODUCT_B,
                                TestHelper.SHOP2
                        )
                );

        mockMvc.perform(get("/balance/product/" + TestHelper.PRODUCT_B_ID + "/shop/" + TestHelper.SHOP2_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.code").value(TestHelper.PRODUCT_B_CODE))
                .andExpect(jsonPath("$.shop.id").value(TestHelper.SHOP2_ID))
                .andExpect(jsonPath("$.spent").value(TestHelper.SALE2B_SPENT_AMOUNT))
                .andExpect(jsonPath("$.income").value(TestHelper.SALE2B_INCOME_AMOUNT));
    }

    @Test
    void getBalancesPerProductPerShop() throws Exception {
        when(balanceService.getBalancesPerProductPerShop(null, null)).thenReturn(List.of(
                        new Balance(
                                TestHelper.SALE1A_SPENT_AMOUNT,
                                TestHelper.SALE1A_INCOME_AMOUNT,
                                TestHelper.PRODUCT_A,
                                TestHelper.SHOP1
                        ),
                        new Balance(
                                TestHelper.SALE1B_SPENT_AMOUNT,
                                TestHelper.SALE1B_INCOME_AMOUNT,
                                TestHelper.PRODUCT_B,
                                TestHelper.SHOP1
                        ),
                        new Balance(
                                TestHelper.SALE2A_SPENT_AMOUNT,
                                TestHelper.SALE2A_INCOME_AMOUNT,
                                TestHelper.PRODUCT_A,
                                TestHelper.SHOP2
                        ),
                        new Balance(
                                TestHelper.SALE2B_SPENT_AMOUNT,
                                TestHelper.SALE2B_INCOME_AMOUNT,
                                TestHelper.PRODUCT_B,
                                TestHelper.SHOP2
                        )
                )
        );

        mockMvc.perform(get("/balance/product/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(TestHelper.PRODUCT_A_ID))
                .andExpect(jsonPath("$[0].shop.id").value(TestHelper.SHOP1_ID))
                .andExpect(jsonPath("$[0].spent").value(TestHelper.SALE1A_SPENT_AMOUNT))
                .andExpect(jsonPath("$[0].income").value(TestHelper.SALE1A_INCOME_AMOUNT))
                .andExpect(jsonPath("$[1].product.id").value(TestHelper.PRODUCT_B_ID))
                .andExpect(jsonPath("$[1].shop.id").value(TestHelper.SHOP1_ID))
                .andExpect(jsonPath("$[1].spent").value(TestHelper.SALE1B_SPENT_AMOUNT))
                .andExpect(jsonPath("$[1].income").value(TestHelper.SALE1B_INCOME_AMOUNT))
                .andExpect(jsonPath("$[2].product.id").value(TestHelper.PRODUCT_A_ID))
                .andExpect(jsonPath("$[2].shop.id").value(TestHelper.SHOP2_ID))
                .andExpect(jsonPath("$[2].spent").value(TestHelper.SALE2A_SPENT_AMOUNT))
                .andExpect(jsonPath("$[2].income").value(TestHelper.SALE2A_INCOME_AMOUNT))
                .andExpect(jsonPath("$[3].product.id").value(TestHelper.PRODUCT_B_ID))
                .andExpect(jsonPath("$[3].shop.id").value(TestHelper.SHOP2_ID))
                .andExpect(jsonPath("$[3].spent").value(TestHelper.SALE2B_SPENT_AMOUNT))
                .andExpect(jsonPath("$[3].income").value(TestHelper.SALE2B_INCOME_AMOUNT));
    }
}