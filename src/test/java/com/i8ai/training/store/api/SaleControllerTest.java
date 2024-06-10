package com.i8ai.training.store.api;

import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.service.SaleService;
import com.i8ai.training.store.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

@WebMvcTest(SaleController.class)
class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaleService saleService;

    @Test
    void testGetSales() throws Exception {
        List<Sale> sales = Arrays.asList(Sale.builder().build(), Sale.builder().build());
        given(saleService.getSales(null, null, null, null)).willReturn(sales);

        mockMvc.perform(get("/sale"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testRegisterSale() throws Exception {
        Sale newSale = Sale.builder().build();
        Sale savedSale = Sale.builder().build();
        given(saleService.registerSale(any(Sale.class))).willReturn(savedSale);

        mockMvc.perform(post("/sale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(newSale)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testDeleteSale() throws Exception {
        doNothing().when(saleService).deleteSale(anyLong());

        mockMvc.perform(delete("/sale/1"))
                .andExpect(status().isOk());
    }
}
