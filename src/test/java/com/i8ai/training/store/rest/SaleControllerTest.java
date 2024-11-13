package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.rest.dto.SaleDto;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class SaleControllerTest {

    private final MockMvc mockMvc;

    private final TestHelper helper;

    private Offer offer1A;
    private Offer offer1B;
    private Offer offer2A;
    private Offer offer2B;

    @BeforeEach
    void setUp() {
        Shop shop1 = helper.createShop1();
        Shop shop2 = helper.createShop2();

        Lot lotA = helper.createLotA(helper.createProductA());
        Lot lotB = helper.createLotB(helper.createProductB());

        offer1A = helper.createOffer(helper.createPack1A(lotA, shop1), TestConstants.PRODUCT_A_PRICE);
        offer1B = helper.createOffer(helper.createPack1B(lotB, shop1), TestConstants.PRODUCT_B_PRICE);
        offer2A = helper.createOffer(helper.createPack2A(lotA, shop2), TestConstants.PRODUCT_A_PRICE);
        offer2B = helper.createOffer(helper.createPack2B(lotB, shop2), TestConstants.PRODUCT_B_PRICE);
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
    void testRegisterSale() throws Exception {
        Sale sale = Sale.builder()
                .offer(offer1A)
                .amount(TestConstants.SALE_1A40_AMOUNT)
                .registeredAt(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(helper.asJsonString(new SaleDto(sale))))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetSales() throws Exception {
        helper.createSale(offer1A, TestConstants.SALE_1A35_AMOUNT);
        helper.createSale(offer1B, TestConstants.SALE_1B45_AMOUNT);
        helper.createSale(offer2A, TestConstants.SALE_2A55_AMOUNT);
        helper.createSale(offer2B, TestConstants.SALE_2B65_AMOUNT);

        mockMvc.perform(get("/sales"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath("$[2]").exists())
                .andExpect(jsonPath("$[3]").exists());
    }

    @Test
    void testDeleteSale() throws Exception {
        Sale sale2B = helper.createSale(offer2B, TestConstants.SALE_2B70_AMOUNT);

        mockMvc.perform(delete("/sales/{saleId}", sale2B.getId()))
                .andExpect(status().isOk());
    }
}
