package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Lot;
import com.i8ai.training.storeapi.domain.Pack;
import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.domain.Shop;
import com.i8ai.training.storeapi.repository.LotRepository;
import com.i8ai.training.storeapi.repository.PackRepository;
import com.i8ai.training.storeapi.repository.ProductRepository;
import com.i8ai.training.storeapi.repository.ShopRepository;
import com.i8ai.training.storeapi.service.PackService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PackServiceImplTest {
    private static final String PRODUCT_A_CODE = "a_product_code";
    private static final String PRODUCT_B_CODE = "b_product_code";
    private static final String PRODUCT_A_NAME = "a_product_name";
    private static final String PRODUCT_B_NAME = "b_product_name";
    private static final String PRODUCT_A_MEASURE = "a_product_measure";
    private static final String PRODUCT_B_MEASURE = "b_product_measure";
    private static final String SHOP1_NAME = "name of shop 1";
    private static final String SHOP1_ADDRESS = "address of shop 1";
    private static final String SHOP2_NAME = "name of shop 2";
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
    private PackService packService;

    private Product productA;
    private Product productB;

    private Lot lotA;
    private Lot lotB;

    private Shop shop1;
    private Shop shop2;

    private Pack removablePack;

    @BeforeEach
    void setUp() {
        productA = productRepository.save(new Product(null, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null));
        productB = productRepository.save(new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null));

        lotA = lotRepository.save(new Lot(null, new Date(1), 500.0, 5.5, productA));
        lotB = lotRepository.save(new Lot(null, new Date(2), 800.0, 8.0, productB));

        shop1 = shopRepository.save(new Shop(null, SHOP1_NAME, SHOP1_ADDRESS, null));
        shop2 = shopRepository.save(new Shop(null, SHOP2_NAME, SHOP2_ADDRESS, null));

        packRepository.save(new Pack(null, new Date(4), 100.0, lotB, shop1));
        packRepository.save(new Pack(null, new Date(5), 50.0, lotA, shop2));
        packRepository.save(new Pack(null, new Date(6), 70.0, lotB, shop2));
        packRepository.save(new Pack(null, new Date(7), 150.0, lotA, shop1));
        removablePack = packRepository.save(new Pack(null, new Date(9), 50.0, lotA, shop2));
    }

    @AfterEach
    void tearDown() {
        packRepository.deleteAll();
        lotRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void getPacksWithAllFilters() {
        List<Pack> pack = packService.getPacks(new Date(3), new Date(6), productA.getId(), shop2.getId());
        assertEquals(1, pack.size());
    }

    @Test
    void getPacksWithShopFilter() {
        List<Pack> pack = packService.getPacks(null, null, null, shop1.getId());
        assertEquals(2, pack.size());
    }

    @Test
    void getPackWithProductFilter() {
        List<Pack> pack = packService.getPacks(null, null, productB.getId(), null);
        assertEquals(2, pack.size());
    }

    @Test
    void getAllPack() {
        List<Pack> pack = packService.getPacks(null, null, null, null);
        assertEquals(5, pack.size());
    }

    @Test
    void registerPackWithNotValidAmount() {
        Exception e = assertThrows(RuntimeException.class, () ->
                packService.registerPack(new Pack(null, new Date(), 1000.0, lotA, shop1)));
        assertTrue(e.getMessage().contains("enough"));
    }

    @Test
    void registerPack() {
        packService.registerPack(new Pack(null, new Date(), 100.0, lotB, shop1));
    }

    @Test
    void deletePack() {
        packService.deletePack(removablePack.getId());
    }
}
