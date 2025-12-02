package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.rest.dto.OfferDto;
import com.i8ai.training.store.util.TestHelper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.i8ai.training.store.util.TestConstants.PRODUCT_A_PRICE;
import static com.i8ai.training.store.util.TestConstants.PRODUCT_B_PRICE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class OfferControllerTest {

    private final MockMvc mockMvc;

    private final TestHelper helper;

    Pack pack1A;
    Pack pack1B;
    Pack pack2A;
    Pack pack2B;

    @BeforeEach
    void setUp() {
        Shop shop1 = helper.createShop1();
        Shop shop2 = helper.createShop2();

        Lot lotA = helper.createLotA(helper.createProductA());
        Lot lotB = helper.createLotB(helper.createProductB());

        pack1A = helper.createPack1A(lotA, shop1);
        pack1B = helper.createPack1B(lotB, shop1);
        pack2A = helper.createPack2A(lotA, shop2);
        pack2B = helper.createPack2B(lotB, shop2);
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
    void testCreateOffer() throws Exception {
        Offer offer = Offer.builder()
                .pack(pack1A)
                .price(PRODUCT_A_PRICE)
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(helper.asJsonString(new OfferDto(offer))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());


    }

    @Test
    void testGetCurrentOffers() throws Exception {
        helper.createOffer(pack1A, PRODUCT_A_PRICE);
        helper.createOffer(pack1B, PRODUCT_B_PRICE);
        helper.createOffer(pack2A, PRODUCT_A_PRICE);
        helper.createOffer(pack2B, PRODUCT_B_PRICE);

        mockMvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath("$[2]").exists())
                .andExpect(jsonPath("$[3]").exists());
    }

    @Test
    void testGetOffersHistory() throws Exception {
        helper.createOffer(pack1A, PRODUCT_A_PRICE);
        helper.createOffer(pack1B, PRODUCT_B_PRICE);
        helper.createOffer(pack2A, PRODUCT_A_PRICE);
        helper.createOffer(pack2B, PRODUCT_B_PRICE);

        mockMvc.perform(get("/offers/history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath("$[2]").exists())
                .andExpect(jsonPath("$[3]").exists());
    }
}
