package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.*;
import com.i8ai.training.storeapi.repository.*;
import com.i8ai.training.storeapi.service.ExistenceService;
import com.i8ai.training.storeapi.service.dto.ExistenceDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ExistenceServiceImplTest {
    private static final String PRODUCT_A_CODE = "a_product_code";
    private static final String PRODUCT_B_CODE = "b_product_code";
    private static final String PRODUCT_A_NAME = "a_product_name";
    private static final String PRODUCT_B_NAME = "b_product_name";
    private static final String PRODUCT_A_MEASURE = "a_product_measure";
    private static final String PRODUCT_B_MEASURE = "b_product_measure";
    private static final String SHOP1_NAME = "name of shop 1";
    private static final String SHOP2_NAME = "name of shop 2";
    private static final String SHOP1_ADDRESS = "address of shop 1";
    private static final String SHOP2_ADDRESS = "address of shop 2";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ExistenceService existenceService;

    private Product productA;
    private Product productB;

    private Shop shop1;


    @BeforeEach
    void setUp() {
        productA = productRepository.save(new Product(null, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null));
        productB = productRepository.save(new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null));

        Lot lotA = lotRepository.save(new Lot(null, new Date(1), 500.0, 5.5, productA));
        Lot lotB = lotRepository.save(new Lot(null, new Date(2), 800.0, 8.0, productB));

        shop1 = shopRepository.save(new Shop(null, SHOP1_NAME, SHOP1_ADDRESS, null));
        Shop shop2 = shopRepository.save(new Shop(null, SHOP2_NAME, SHOP2_ADDRESS, null));

        Pack packA1 = packRepository.save(new Pack(null, new Date(3), 200.0, lotA, shop1));
        Pack packA2 = packRepository.save(new Pack(null, new Date(4), 150.0, lotA, shop2));
        Pack packB1 = packRepository.save(new Pack(null, new Date(5), 200.0, lotB, shop1));
        Pack packB2 = packRepository.save(new Pack(null, new Date(6), 300.0, lotB, shop2));

        saleRepository.save(new Sale(null, new Date(3), 5.0, 8.0, packA1));
        saleRepository.save(new Sale(null, new Date(5), 1.0, 9.0, packA2));
        saleRepository.save(new Sale(null, new Date(7), 3.0, 15.0, packB1));
        saleRepository.save(new Sale(null, new Date(9), 9.0, 12.0, packB2));
        saleRepository.save(new Sale(null, new Date(15), 2.0, 8.0, packA1));
    }

    @AfterEach
    void tearDown() {
        SaleServiceImplTest.cleanDatabase(saleRepository, packRepository, lotRepository, productRepository, shopRepository);
    }

    @Test
    void getProductExistenceInMain() {
        assertEquals(300.0, existenceService.getProductExistenceInMain(productB.getId()).getAmount());
    }

    @Test
    void getAllProductsExistenceInMain() {
        List<ExistenceDTO> existences = existenceService.getAllProductsExistenceInMain();
        assertEquals(2, existences.size());
        assertNull(existences.get(0).getShop());
        assertEquals(existences.get(0).getProduct().getName(), PRODUCT_A_NAME);
        assertEquals(150.0, existences.get(0).getAmount());
        assertNull(existences.get(1).getShop());
        assertEquals(existences.get(1).getProduct().getName(), PRODUCT_B_NAME);
        assertEquals(300.0, existences.get(1).getAmount());
    }

    @Test
    void getProductExistenceInShop() {
        assertEquals(193.0, existenceService.getProductExistenceInShop(productA.getId(), shop1.getId()).getAmount());
    }

    @Test
    void getProductExistenceInAllShops() {
        List<ExistenceDTO> existences = existenceService.getProductExistenceInAllShops(productB.getId());
        assertEquals(2, existences.size());
        assertEquals(existences.get(0).getShop().getName(), SHOP1_NAME);
        assertEquals(existences.get(0).getProduct().getName(), PRODUCT_B_NAME);
        assertEquals(197.0, existences.get(0).getAmount());
        assertEquals(existences.get(1).getShop().getName(), SHOP2_NAME);
        assertEquals(existences.get(1).getProduct().getName(), PRODUCT_B_NAME);
        assertEquals(291.0, existences.get(1).getAmount());
    }

}
