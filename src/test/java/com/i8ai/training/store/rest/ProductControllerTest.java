package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.util.TestHelper;
import com.i8ai.training.store.util.TestUtils;
import org.junit.jupiter.api.AfterEach;
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
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestHelper helper;

    @AfterEach
    void tearDown() {
        helper.deleteAllProducts();
    }

    @Test
    void testGetAllProducts() throws Exception {
        helper.createProductA();
        helper.createProductB();

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testAddProduct() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(helper.createProductB())))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetProduct() throws Exception {
        Long productId = helper.createProductA().getId();

        mockMvc.perform(get("/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testReplaceProduct() throws Exception {
        Product product = helper.createProductA();
        product.setName(TestUtils.PRODUCT_B_NAME);

        mockMvc.perform(put("/products/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product product = helper.createProductB();

        mockMvc.perform(delete("/products/{productId}", product.getId()))
                .andExpect(status().isOk());
    }
}
