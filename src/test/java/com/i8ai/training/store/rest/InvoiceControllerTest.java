package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Invoice;
import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.util.TestHelper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.i8ai.training.store.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class InvoiceControllerTest {

    private final MockMvc mockMvc;

    private final TestHelper helper;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        Shop shop1 = helper.createShop1();

        Lot lotA = helper.createLotA(helper.createProductA());
        Lot lotB = helper.createLotB(helper.createProductB());

        Offer offer1A = helper.createOffer(helper.createPack1A(lotA, shop1), PRODUCT_A_PRICE);
        Offer offer1B = helper.createOffer(helper.createPack1B(lotB, shop1), PRODUCT_B_PRICE);

        invoice = helper.createInvoice();

        helper.createSale(invoice, offer1A, PACK1A_AMOUNT);
        helper.createSale(invoice, offer1B, PACK1B_AMOUNT);
    }

    @AfterEach
    void tearDown() {
        helper.deleteAllSales();
        helper.deleteAllInvoices();
        helper.deleteAllOffers();
        helper.deleteAllPacks();
        helper.deleteAllLots();
        helper.deleteAllProducts();
        helper.deleteAllShops();
    }

    @Test
    void testCreateInvoice() throws Exception {
        mockMvc.perform(post("/invoices"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetInvoice() throws Exception {
        mockMvc.perform(get("/invoices/{invoiceId}", invoice.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.sales").isArray())
                .andExpect(jsonPath("$.sales[0]").exists())
                .andExpect(jsonPath("$.sales[1]").exists());
    }
}