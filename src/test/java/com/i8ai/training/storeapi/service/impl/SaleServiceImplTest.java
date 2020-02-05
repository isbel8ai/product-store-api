package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.*;
import com.i8ai.training.storeapi.repository.*;
import com.i8ai.training.storeapi.service.SaleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SaleServiceImplTest {
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
    private SaleService saleService;

    private Product productA;
    private Product productB;

    private Shop shop1;
    private Shop shop2;

    private Pack packA1;

    private Sale removableSale;

    static void cleanDatabase(SaleRepository saleRepository, PackRepository packRepository, LotRepository lotRepository, ProductRepository productRepository, ShopRepository shopRepository) {
        saleRepository.deleteAll();
        packRepository.deleteAll();
        lotRepository.deleteAll();
        productRepository.deleteAll();
        shopRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        productA = productRepository.save(new Product(null, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null));
        productB = productRepository.save(new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null));

        Lot lotA = lotRepository.save(new Lot(null, new Date(1), 500.0, 5.5, productA));
        Lot lotB = lotRepository.save(new Lot(null, new Date(2), 800.0, 8.0, productB));

        shop1 = shopRepository.save(new Shop(null, SHOP1_NAME, SHOP1_ADDRESS, null));
        shop2 = shopRepository.save(new Shop(null, SHOP2_NAME, SHOP2_ADDRESS, null));

        packA1 = packRepository.save(new Pack(null, new Date(3), 200.0, lotA, shop1));
        Pack packA2 = packRepository.save(new Pack(null, new Date(4), 150.0, lotA, shop2));
        Pack packB1 = packRepository.save(new Pack(null, new Date(5), 200.0, lotB, shop1));
        Pack packB2 = packRepository.save(new Pack(null, new Date(6), 300.0, lotB, shop2));

        saleRepository.save(new Sale(null, new Date(3), 5.0, 8.0, packA1));
        saleRepository.save(new Sale(null, new Date(5), 1.0, 9.0, packA2));
        saleRepository.save(new Sale(null, new Date(7), 3.0, 15.0, packB1));
        saleRepository.save(new Sale(null, new Date(9), 9.0, 12.0, packB2));
        removableSale = saleRepository.save(new Sale(null, new Date(15), 2.0, 8.0, packA1));
    }

    @AfterEach
    void tearDown() {
        cleanDatabase(saleRepository, packRepository, lotRepository, productRepository, shopRepository);
    }

    @Test
    void getSalesWithAllFilters() {
        List<Sale> sales = saleService.getSales(new Date(2), new Date(7), productA.getId(), shop1.getId());
        assertEquals(1, sales.size());
    }

    @Test
    void getSalesWithTimeFilter() {
        List<Sale> sales = saleService.getSales(new Date(4), new Date(14), null, null);
        assertEquals(3, sales.size());
    }

    @Test
    void getSalesWithShopFilter() {
        List<Sale> sales = saleService.getSales(null, null, null, shop2.getId());
        assertEquals(2, sales.size());
    }

    @Test
    void getSalesWithProductFilter() {
        List<Sale> sales = saleService.getSales(null, null, productB.getId(), null);
        assertEquals(2, sales.size());
    }

    @Test
    void getAllSales() {
        List<Sale> sales = saleService.getSales(null, null, null, null);
        assertEquals(5, sales.size());
    }

    @Test
    void registerSaleWithNoValidAmount() {
        Exception e = assertThrows(RuntimeException.class, () ->
                saleService.registerSale(new Sale(null, new Date(20), 500.0, 8.0, packA1)));
        assertTrue(e.getMessage().contains("enough"));
    }

    @Test
    void registerSale() {
        assertDoesNotThrow(() ->
                saleService.registerSale(new Sale(null, new Date(20), 2.0, 8.0, packA1))
        );
    }

    @Test
    void deleteSale() {
        assertDoesNotThrow(() ->
                saleService.deleteSale(removableSale.getId())
        );
    }

    @Test
    void getProductSoldInShopAmount() {
        assertEquals(7, saleService.getProductSoldInShopAmount(productA.getId(), shop1.getId()));
    }
}
