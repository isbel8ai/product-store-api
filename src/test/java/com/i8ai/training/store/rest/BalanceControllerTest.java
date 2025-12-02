package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Invoice;
import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.util.TestConstants;
import com.i8ai.training.store.util.TestHelper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.i8ai.training.store.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class BalanceControllerTest {

    private final MockMvc mockMvc;

    private final TestHelper helper;

    Long idShop1;
    Long idShop2;

    Long idProductA;
    Long idProductB;

    @BeforeEach
    void setUp() {
        Shop shop1 = helper.createShop1();
        Shop shop2 = helper.createShop2();
        idShop1 = shop1.getId();
        idShop2 = shop2.getId();

        Lot lotA = helper.createLotA(helper.createProductA());
        Lot lotB = helper.createLotB(helper.createProductB());
        idProductA = lotA.getProduct().getId();
        idProductB = lotB.getProduct().getId();

        Offer offer1A = helper.createOffer(helper.createPack1A(lotA, shop1), TestConstants.PRODUCT_A_PRICE);
        Offer offer1B = helper.createOffer(helper.createPack1B(lotB, shop1), TestConstants.PRODUCT_B_PRICE);
        Offer offer2A = helper.createOffer(helper.createPack2A(lotA, shop2), TestConstants.PRODUCT_A_PRICE);
        Offer offer2B = helper.createOffer(helper.createPack2B(lotB, shop2), TestConstants.PRODUCT_B_PRICE);

        Invoice invoiceFirst = helper.createInvoice();
        Invoice invoiceSecond = helper.createInvoice();

        helper.createSale(invoiceFirst, offer1A, SALE_1A35_AMOUNT);
        helper.createSale(invoiceSecond, offer1A, SALE_1A40_AMOUNT);
        helper.createSale(invoiceFirst, offer1B, SALE_1B45_AMOUNT);
        helper.createSale(invoiceSecond, offer1B, SALE_1B50_AMOUNT);
        helper.createSale(invoiceFirst, offer2A, SALE_2A55_AMOUNT);
        helper.createSale(invoiceSecond, offer2A, SALE_2A60_AMOUNT);
        helper.createSale(invoiceFirst, offer2B, SALE_2B65_AMOUNT);
        helper.createSale(invoiceSecond, offer2B, SALE_2B70_AMOUNT);
    }

    @AfterEach
    void tearDown() {
        helper.deleteAllSales();
        helper.deleteAllOffers();
        helper.deleteAllPacks();
        helper.deleteAllLots();
        helper.deleteAllProducts();
        helper.deleteAllShops();
    }

    @Test
    void getNetBalance() throws Exception {
        mockMvc.perform(get("/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spent").value(NET_SALES_EXPENSES))
                .andExpect(jsonPath("$.income").value(NET_SALES_INCOME));
    }

    @Test
    void getBalancesPerProduct() throws Exception {
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
        mockMvc.perform(get("/balance/product/{productId}", idProductA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.code").value(PRODUCT_A_CODE))
                .andExpect(jsonPath("$.spent").value(PRODUCT_A_EXPENSES))
                .andExpect(jsonPath("$.income").value(PRODUCT_A_INCOME));
    }

    @Test
    void getBalancesByProductPerShop() throws Exception {
        mockMvc.perform(get("/balance/product/{productId}/shop", idProductB))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(idProductB))
                .andExpect(jsonPath("$[0].shop.id").value(idShop1))
                .andExpect(jsonPath("$[0].spent").value(PACK1A_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(PACK1A_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(idProductB))
                .andExpect(jsonPath("$[1].shop.id").value(idShop2))
                .andExpect(jsonPath("$[1].spent").value(PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(PACK2B_SALES_INCOME));
    }

    @Test
    void getBalancesPerShop() throws Exception {
        mockMvc.perform(get("/balance/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shop.id").value(idShop1))
                .andExpect(jsonPath("$[0].spent").value(SHOP1_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(SHOP1_INCOME))
                .andExpect(jsonPath("$[1].shop.id").value(idShop2))
                .andExpect(jsonPath("$[1].spent").value(SHOP2_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(SHOP2_INCOME));
    }

    @Test
    void getBalanceByShop() throws Exception {
        mockMvc.perform(get("/balance/shop/{shopId}", idShop1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shop.id").value(idShop1))
                .andExpect(jsonPath("$.spent").value(SHOP1_EXPENSES))
                .andExpect(jsonPath("$.income").value(SHOP1_INCOME));
    }

    @Test
    void getBalancesByShopPerProduct() throws Exception {
        mockMvc.perform(get("/balance/shop/{shopId}/product", idShop2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(idProductA))
                .andExpect(jsonPath("$[0].shop.id").value(idShop2))
                .andExpect(jsonPath("$[0].spent").value(PACK2A_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(PACK2A_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(idProductB))
                .andExpect(jsonPath("$[1].shop.id").value(idShop2))
                .andExpect(jsonPath("$[1].spent").value(PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(PACK2B_SALES_INCOME));
    }

    @Test
    void getBalanceByProductAndShop() throws Exception {
        mockMvc.perform(get("/balance/product/{productId}/shop/{shopId}", idProductB, idShop2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.code").value(PRODUCT_B_CODE))
                .andExpect(jsonPath("$.shop.id").value(idShop2))
                .andExpect(jsonPath("$.spent").value(PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$.income").value(PACK2B_SALES_INCOME));
    }

    @Test
    void getBalancesPerProductPerShop() throws Exception {
        mockMvc.perform(get("/balance/product/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(idProductA))
                .andExpect(jsonPath("$[0].shop.id").value(idShop1))
                .andExpect(jsonPath("$[0].spent").value(PACK1A_SALES_EXPENSES))
                .andExpect(jsonPath("$[0].income").value(PACK1A_SALES_INCOME))
                .andExpect(jsonPath("$[1].product.id").value(idProductB))
                .andExpect(jsonPath("$[1].shop.id").value(idShop1))
                .andExpect(jsonPath("$[1].spent").value(PACK1B_SALES_EXPENSES))
                .andExpect(jsonPath("$[1].income").value(PACK1B_SALES_INCOME))
                .andExpect(jsonPath("$[2].product.id").value(idProductA))
                .andExpect(jsonPath("$[2].shop.id").value(idShop2))
                .andExpect(jsonPath("$[2].spent").value(PACK2A_SALES_EXPENSES))
                .andExpect(jsonPath("$[2].income").value(PACK2A_SALES_INCOME))
                .andExpect(jsonPath("$[3].product.id").value(idProductB))
                .andExpect(jsonPath("$[3].shop.id").value(idShop2))
                .andExpect(jsonPath("$[3].spent").value(PACK2B_SALES_EXPENSES))
                .andExpect(jsonPath("$[3].income").value(PACK2B_SALES_INCOME));
    }
}
