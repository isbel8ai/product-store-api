package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.service.ShopService;
import com.i8ai.training.store.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    void testGetAllShops() throws Exception {
        List<Shop> shops = Arrays.asList(Shop.builder().build(), Shop.builder().build());
        given(shopService.getAllShops()).willReturn(shops);

        mockMvc.perform(get("/shops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testAddShop() throws Exception {
        Shop newShop = Shop.builder().build();
        Shop savedShop = Shop.builder().build();
        given(shopService.createShop(any(Shop.class))).willReturn(savedShop);

        mockMvc.perform(post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(newShop)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetShop() throws Exception {
        Shop shop = Shop.builder().build();
        given(shopService.getShop(anyLong())).willReturn(shop);

        mockMvc.perform(get("/shops/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testReplaceShop() throws Exception {
        Shop modifiedShop = Shop.builder().build();
        given(shopService.replaceShop(anyLong(), any(Shop.class))).willReturn(modifiedShop);

        mockMvc.perform(put("/shops/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(modifiedShop)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testDeleteShop() throws Exception {
        doNothing().when(shopService).deleteShop(anyLong());

        mockMvc.perform(delete("/shops/1"))
                .andExpect(status().isOk());
    }
}
