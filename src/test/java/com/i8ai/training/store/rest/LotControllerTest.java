package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.rest.dto.LotDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class LotControllerTest {

    private final MockMvc mockMvc;

    private final TestHelper helper;

    private Product productA;
    private Product productB;

    @BeforeEach
    void setUp() {
        productA = helper.createProductA();
        productB = helper.createProductB();
    }

    @AfterEach
    void tearDown() {
        helper.deleteAllLots();
        helper.deleteAllProducts();
    }

    @Test
    void testRegisterLot() throws Exception {
        mockMvc.perform(post("/lots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(helper.asJsonString(new LotDto(helper.createLotA(productA)))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetLots() throws Exception {
        helper.createLotA(productA);
        helper.createLotA(productB);

        mockMvc.perform(get("/lots"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testDeleteLot() throws Exception {
        Lot lot = helper.createLotB(productB);

        mockMvc.perform(delete("/lots/{lotId}", lot.getId()))
                .andExpect(status().isOk());
    }
}
