package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.rest.dto.PackDto;
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
class PackControllerTest {

    private final MockMvc mockMvc;

    private final TestHelper helper;

    private Shop shop1;
    private Shop shop2;
    private Lot lotA;
    private Lot lotB;

    @BeforeEach
    void setUp() {
        shop1 = helper.createShop1();
        shop2 = helper.createShop2();
        lotA = helper.createLotA(helper.createProductA());
        lotB = helper.createLotB(helper.createProductB());
    }

    @AfterEach
    void tearDown() {
        helper.deleteAllPacks();
        helper.deleteAllShops();
        helper.deleteAllLots();
        helper.deleteAllProducts();
    }

    @Test
    void testRegisterPack() throws Exception {
        Pack pack = Pack.builder()
                .lot(lotA)
                .shop(shop1)
                .receivedAmount(TestConstants.PACK1A_AMOUNT)
                .receivedAt(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/packs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(helper.asJsonString(new PackDto(pack))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetPacks() throws Exception {
        helper.createPack1A(lotA, shop1);
        helper.createPack1B(lotB, shop1);
        helper.createPack2A(lotA, shop2);
        helper.createPack2B(lotB, shop2);

        mockMvc.perform(get("/packs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath("$[2]").exists())
                .andExpect(jsonPath("$[3]").exists());
    }

    @Test
    void testDeletePack() throws Exception {
        Pack pack = helper.createPack2B(lotB, shop2);

        mockMvc.perform(delete("/packs/{packId}", pack.getId()))
                .andExpect(status().isOk());
    }
}
