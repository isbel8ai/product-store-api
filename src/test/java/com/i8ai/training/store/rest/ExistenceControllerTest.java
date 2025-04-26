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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.i8ai.training.store.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ExistenceControllerTest {

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
    void testGetAllProductsExistenceInMain() throws Exception {
        mockMvc.perform(get("/existence"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testGetProductExistenceInMain() throws Exception {
        mockMvc.perform(get("/existence/{productId}/main", idProductA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetProductExistenceInAllShops() throws Exception {
        mockMvc.perform(get("/existence/{productId}/shops", idProductB))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testGetProductExistenceInShop() throws Exception {
        mockMvc.perform(get("/existence/{productId}/shops/{shopId}", idProductB, idShop1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }
}
