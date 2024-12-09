package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.util.TestConstants;
import com.i8ai.training.store.util.TestHelper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ProductControllerTest {

    private final MockMvc mockMvc;

    private final TestHelper helper;

    @AfterEach
    void tearDown() {
        helper.deleteAllProducts();
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = Product.builder()
                .code(TestConstants.PRODUCT_A_CODE)
                .name(TestConstants.PRODUCT_A_NAME)
                .measureUnit(TestConstants.PRODUCT_A_MEASURE)
                .build();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(helper.asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
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
    void testGetProduct() throws Exception {
        Long productId = helper.createProductB().getId();

        mockMvc.perform(get("/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testReplaceProduct() throws Exception {
        Product product = helper.createProductA();
        product.setDescription("New description for product A");

        mockMvc.perform(put("/products/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(helper.asJsonString(product)))
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
