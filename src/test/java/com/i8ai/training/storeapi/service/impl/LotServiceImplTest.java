package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Lot;
import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.repository.LotRepository;
import com.i8ai.training.storeapi.repository.ProductRepository;
import com.i8ai.training.storeapi.service.LotService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LotServiceImplTest {

    private static final String PRODUCT_A_CODE = "a_product_code";
    private static final String PRODUCT_B_CODE = "b_product_code";
    private static final String PRODUCT_A_NAME = "a_product_name";
    private static final String PRODUCT_B_NAME = "b_product_name";
    private static final String PRODUCT_A_MEASURE = "a_product_measure";
    private static final String PRODUCT_B_MEASURE = "b_product_measure";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private LotService lotService;

    private Product productA;
    private Product productB;

    private Lot removableLot;

    @BeforeEach
    void setUp() {
        productA = productRepository.save(new Product(null, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null));
        productB = productRepository.save(new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null));
        lotRepository.save(new Lot(null, new Date(8000), 5.5, 50.0, productA));
        lotRepository.save(new Lot(null, new Date(2500), 8.0, 80.0, productB));
        lotRepository.save(new Lot(null, new Date(6500), 9.5, 30.0, productB));
        lotRepository.save(new Lot(null, new Date(5000), 2.0, 10.0, productA));
        removableLot = lotRepository.save(new Lot(null, new Date(2000), 7.0, 20.0, productA));
    }

    @AfterEach
    void tearDown() {
        lotRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void getLotsWithAllFilters() {
        List<Lot> lots = lotService.getLots(new Date(2000), new Date(5000), productB.getId());
        assertEquals(1, lots.size());
    }

    @Test
    void getLotsWithProductId() {
        List<Lot> lots = lotService.getLots(null, null, productA.getId());
        assertEquals(3, lots.size());
    }

    @Test
    void getLotsWithStartDateAndEndDate() {
        List<Lot> lots = lotService.getLots(new Date(5000), new Date(6500), null);
        assertEquals(2, lots.size());
    }

    @Test
    void getLotsWithEndDate() {
        List<Lot> lots = lotService.getLots(null, new Date(6500), null);
        assertEquals(4, lots.size());
    }

    @Test
    void getLotsWithStartDate() {
        List<Lot> lots = lotService.getLots(new Date(5000), null, null);
        assertEquals(3, lots.size());
    }

    @Test
    void getAllLots() {
        List<Lot> lots = lotService.getLots(null, null, null);
        assertEquals(5, lots.size());
    }

    @Test
    void registerLot() {
        assertNotNull(lotService.registerLot(new Lot(null, new Date(7000), 3.5, 650.0, productB)));
    }

    @Test
    void deleteLot() {
        lotService.deleteLot(removableLot.getId());
    }

    @Test
    void getProductReceivedAmount() {
        assertEquals(110, lotService.getProductReceivedAmount(productB.getId()));
        assertEquals(80, lotService.getProductReceivedAmount(productA.getId()));
    }

}
