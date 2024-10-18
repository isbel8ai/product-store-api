package com.i8ai.training.store.rest;

import com.i8ai.training.store.service.ExistenceService;
import com.i8ai.training.store.service.data.Existence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExistenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExistenceService existenceService;

    @Test
    void testGetAllProductsExistenceInMain() throws Exception {
        List<Existence> existences = Arrays.asList(Existence.builder().build(), Existence.builder().build());
        given(existenceService.getAllProductsExistenceInMain()).willReturn(existences);

        mockMvc.perform(get("/existence"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testGetProductExistenceInMain() throws Exception {
        Existence existence = Existence.builder().build();
        given(existenceService.getProductExistenceInMain(anyLong())).willReturn(existence);

        mockMvc.perform(get("/existence/1/main"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetProductExistenceInAllShops() throws Exception {
        List<Existence> existences = Arrays.asList(Existence.builder().build(), Existence.builder().build());
        given(existenceService.getProductExistenceInAllShops(anyLong())).willReturn(existences);

        mockMvc.perform(get("/existence/1/shops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testGetProductExistenceInShop() throws Exception {
        Existence existence = Existence.builder().build();
        given(existenceService.getProductExistenceInShop(anyLong(), anyLong())).willReturn(existence);

        mockMvc.perform(get("/existence/1/shops/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }
}
