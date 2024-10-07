package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.service.LotService;
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

@WebMvcTest(LotController.class)
class LotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotService lotService;

    @Test
    void testGetLots() throws Exception {
        List<Lot> lots = Arrays.asList(Lot.builder().build(), Lot.builder().build());

        given(lotService.getLots(null, null, null)).willReturn(lots);

        mockMvc.perform(get("/lot"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testRegisterLot() throws Exception {
        Lot newLot = Lot.builder().build();
        Lot savedLot = Lot.builder().build();
        given(lotService.registerLot(any(Lot.class))).willReturn(savedLot);

        mockMvc.perform(post("/lot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(newLot)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testDeleteLot() throws Exception {
        doNothing().when(lotService).deleteLot(anyLong());

        mockMvc.perform(delete("/lot/1"))
                .andExpect(status().isOk());
    }
}
