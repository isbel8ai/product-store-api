package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.service.PackService;
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

@WebMvcTest(PackController.class)
class PackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackService packService;

    @Test
    void testGetPacks() throws Exception {
        List<Pack> packs = Arrays.asList(Pack.builder().build(), Pack.builder().build());
        given(packService.getPacks(null, null, null, null)).willReturn(packs);

        mockMvc.perform(get("/pack"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testRegisterPack() throws Exception {
        Pack newPack = Pack.builder().build();
        Pack savedPack = Pack.builder().build();
        given(packService.registerPack(any(Pack.class))).willReturn(savedPack);

        mockMvc.perform(post("/pack")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(newPack)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testDeletePack() throws Exception {
        doNothing().when(packService).deletePack(anyLong());

        mockMvc.perform(delete("/pack/1"))
                .andExpect(status().isOk());
    }
}
