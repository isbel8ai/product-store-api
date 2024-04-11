package com.i8ai.training.storeapi.rest;

import com.i8ai.training.storeapi.model.Lot;
import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Shop;
import com.i8ai.training.storeapi.service.BalanceService;
import com.i8ai.training.storeapi.service.data.Balance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BalanceController.class)
class BalanceControllerTest {

    private static final Product PRODUCT_A = new Product(PRODUCT_A_ID, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null);
    private static final Product PRODUCT_B = new Product(PRODUCT_B_ID, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null);

    private static final Shop SHOP1 = new Shop(SHOP1_ID, SHOP1_NAME, SHOP1_ADDRESS, null);
    private static final Shop SHOP2 = new Shop(SHOP2_ID, SHOP2_NAME, SHOP2_ADDRESS, null);

    private static final Lot LOT_A = new Lot(LOT_A_ID, new Date(5), LOT_A_AMOUNT, PRODUCT_A_COST, PRODUCT_A);
    private static final Lot LOT_B = new Lot(LOT_B_ID, new Date(10), LOT_B_AMOUNT, PRODUCT_B_COST, PRODUCT_B);

    @MockBean
    BalanceService balanceService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getNetBalance() throws Exception {
        when(balanceService.getNetBalance(null, null)).thenReturn(
                Balance.builder().spent(NET_SALES_EXPENSES).income(NET_SALES_INCOME).build()
        );

        mockMvc.perform(get("/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spent").value(NET_SALES_EXPENSES))
                .andExpect(jsonPath("$.income").value(NET_SALES_INCOME));
    }

    @Test
    void getBalancesPerProduct() throws Exception {
        when(balanceService.getBalancesPerProduct(null, null)).thenReturn(List.of(
                Balance.builder().spent(PRODUCT_A_EXPENSES).income(PRODUCT_A_INCOME).product(PRODUCT_A).build(),
                Balance.builder().spent(PRODUCT_B_EXPENSES).income(PRODUCT_B_INCOME).product(PRODUCT_B).build()
        ));

        mockMvc.perform(get("/balance/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.code").value(PRODUCT_A_CODE))
                .andExpect(jsonPath("$[0].spent").value(PRODUCT_A_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(PRODUCT_A_INCOME))
                .andExpect(jsonPath("$[1].product.code").value(PRODUCT_B_CODE))
                .andExpect(jsonPath("$[1].spent").value(PRODUCT_B_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(PRODUCT_B_INCOME));
    }

    @Test
    void getBalanceByProduct() throws Exception {
        when(balanceService.getBalanceByProduct(PRODUCT_A.getId(), null, null)).thenReturn(
                Balance.builder().spent(PRODUCT_A_EXPENSES).income(PRODUCT_A_INCOME).product(PRODUCT_A).build()
        );

        mockMvc.perform(get("/balance/product/" + PRODUCT_A.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.code").value(PRODUCT_A_CODE))
                .andExpect(jsonPath("$.spent").value(PRODUCT_A_EXPENSES))
                .andExpect(jsonPath("$.income").value(PRODUCT_A_INCOME));
    }

    @Test
    void getBalancesByProductPerShop() throws Exception {
        when(balanceService.getBalancesByProductPerShop(PRODUCT_B.getId(), null, null)).thenReturn(List.of(
                Balance.builder().spent(PACK1A_SALES_EXPENSES).income(PACK1A_SALES_INCOME).product(PRODUCT_A).shop(SHOP1).build(),
                Balance.builder().spent(PACK2B_SALES_EXPENSES).income(PACK2B_SALES_INCOME).product(PRODUCT_B).shop(SHOP2).build()
        ));

        mockMvc.perform(get("/balance/product/" + PRODUCT_B.getId() + "/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(PRODUCT_A.getId()))
                .andExpect(jsonPath("$[0].shop.id").value(SHOP1.getId()))
                .andExpect(jsonPath("$[0].spent").value(PACK1A_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(PACK1A_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(PRODUCT_B.getId()))
                .andExpect(jsonPath("$[1].shop.id").value(SHOP2.getId()))
                .andExpect(jsonPath("$[1].spent").value(PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(PACK2B_SALES_INCOME));
    }

    @Test
    void getBalancesPerShop() throws Exception {
        when(balanceService.getBalancesPerShop(null, null)).thenReturn(List.of(
                Balance.builder().spent(SHOP1_EXPENSES).income(SHOP1_INCOME).shop(SHOP1).build(),
                Balance.builder().spent(SHOP2_EXPENSES).income(SHOP2_INCOME).shop(SHOP2).build()
        ));

        mockMvc.perform(get("/balance/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shop.id").value(SHOP1.getId()))
                .andExpect(jsonPath("$[0].spent").value(SHOP1_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(SHOP1_INCOME))
                .andExpect(jsonPath("$[1].shop.id").value(SHOP2.getId()))
                .andExpect(jsonPath("$[1].spent").value(SHOP2_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(SHOP2_INCOME));
    }

    @Test
    void getBalanceByShop() throws Exception {
        when(balanceService.getBalanceByShop(SHOP1.getId(), null, null))
                .thenReturn(Balance.builder().spent(SHOP1_EXPENSES).income(SHOP1_INCOME).shop(SHOP1).build());

        mockMvc.perform(get("/balance/shop/" + SHOP1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shop.id").value(SHOP1.getId()))
                .andExpect(jsonPath("$.spent").value(SHOP1_EXPENSES))
                .andExpect(jsonPath("$.income").value(SHOP1_INCOME));
    }

    @Test
    void getBalancesByShopPerProduct() throws Exception {
        when(balanceService.getBalancesByShopPerProduct(SHOP2.getId(), null, null)).thenReturn(
                List.of(
                        Balance.builder()
                                .spent(PACK2A_SALES_EXPENSES)
                                .income(PACK2A_SALES_INCOME)
                                .product(PRODUCT_A)
                                .shop(SHOP2)
                                .build(),
                        Balance.builder()
                                .spent(PACK2B_SALES_EXPENSES)
                                .income(PACK2B_SALES_INCOME)
                                .product(PRODUCT_B)
                                .shop(SHOP2)
                                .build()
                )
        );

        mockMvc.perform(get("/balance/shop/" + SHOP2.getId() + "/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(PRODUCT_A.getId()))
                .andExpect(jsonPath("$[0].shop.id").value(SHOP2.getId()))
                .andExpect(jsonPath("$[0].spent").value(PACK2A_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(PACK2A_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(PRODUCT_B.getId()))
                .andExpect(jsonPath("$[1].shop.id").value(SHOP2.getId()))
                .andExpect(jsonPath("$[1].spent").value(PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(PACK2B_SALES_INCOME));
    }

    @Test
    void getBalanceByProductAndShop() throws Exception {
        when(balanceService.getBalanceByProductAndShop(PRODUCT_B.getId(), SHOP2.getId(), null, null))
                .thenReturn(
                        Balance.builder()
                                .spent(PACK2B_SALES_EXPENSES)
                                .income(PACK2B_SALES_INCOME)
                                .product(PRODUCT_B)
                                .shop(SHOP2)
                                .build()
                );

        mockMvc.perform(get("/balance/product/" + PRODUCT_B.getId() + "/shop/" + SHOP2.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.code").value(PRODUCT_B_CODE))
                .andExpect(jsonPath("$.shop.id").value(SHOP2.getId()))
                .andExpect(jsonPath("$.spent").value(PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$.income").value(PACK2B_SALES_INCOME));
    }

    @Test
    void getBalancesPerProductPerShop() throws Exception {
        when(balanceService.getBalancesPerProductPerShop(null, null)).thenReturn(
                List.of(
                        Balance.builder()
                                .spent(PACK1A_SALES_EXPENSES)
                                .income(PACK1A_SALES_INCOME)
                                .product(PRODUCT_A)
                                .shop(SHOP1)
                                .build(),
                        Balance.builder()
                                .spent(PACK1B_SALES_EXPENSES)
                                .income(PACK1B_SALES_INCOME)
                                .product(PRODUCT_B)
                                .shop(SHOP1)
                                .build(),
                        Balance.builder()
                                .spent(PACK2A_SALES_EXPENSES)
                                .income(PACK2A_SALES_INCOME)
                                .product(PRODUCT_A)
                                .shop(SHOP2)
                                .build(),
                        Balance.builder()
                                .spent(PACK2B_SALES_EXPENSES)
                                .income(PACK2B_SALES_INCOME)
                                .product(PRODUCT_B)
                                .shop(SHOP2)
                                .build()
                )
        );

        mockMvc.perform(get("/balance/product/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(PRODUCT_A.getId()))
                .andExpect(jsonPath("$[0].shop.id").value(SHOP1.getId()))
                .andExpect(jsonPath("$[0].spent").value(PACK1A_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(PACK1A_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(PRODUCT_B.getId()))
                .andExpect(jsonPath("$[1].shop.id").value(SHOP1.getId()))
                .andExpect(jsonPath("$[1].spent").value(PACK1B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(PACK1B_SALES_INCOME))
                .andExpect(jsonPath("$[2].product.id").value(PRODUCT_A.getId()))
                .andExpect(jsonPath("$[2].shop.id").value(SHOP2.getId()))
                .andExpect(jsonPath("$[2].spent").value(PACK2A_SALES_EXPENSES))
                .andExpect(jsonPath("$[2].income").value(PACK2A_SALES_INCOME))
                .andExpect(jsonPath("$[3].product.id").value(PRODUCT_B.getId()))
                .andExpect(jsonPath("$[3].shop.id").value(SHOP2.getId()))
                .andExpect(jsonPath("$[3].spent").value(PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[3].income").value(PACK2B_SALES_INCOME));
    }
}
