package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.util.TestConstants;
import com.i8ai.training.store.util.TestHelper;
import lombok.RequiredArgsConstructor;
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
class ShopControllerTest {

    private final MockMvc mockMvc;

    private final TestHelper helper;

    @Test
    void testCreateShop() throws Exception {
        Shop shop = Shop.builder()
                .name(TestConstants.SHOP1_NAME)
                .address(TestConstants.SHOP1_ADDRESS)
                .build();
        mockMvc.perform(post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(helper.asJsonString(shop)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetAllShops() throws Exception {
        helper.createShop1();
        helper.createShop2();

        mockMvc.perform(get("/shops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testGetShop() throws Exception {
        Shop shop1 = helper.createShop1();

        mockMvc.perform(get("/shops/{shopId}", shop1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testReplaceShop() throws Exception {
        Shop shop2 = helper.createShop2();
        shop2.setDescription("New description for shop 2");

        mockMvc.perform(put("/shops/{shopId}", shop2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(helper.asJsonString(shop2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testDeleteShop() throws Exception {
        Shop shop1 = helper.createShop1();

        mockMvc.perform(delete("/shops/{shopId}", shop1.getId()))
                .andExpect(status().isOk());
    }
}
