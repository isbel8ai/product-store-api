package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.*;
import com.i8ai.training.storeapi.repository.*;
import com.i8ai.training.storeapi.service.BalanceService;
import com.i8ai.training.storeapi.service.dto.BalanceDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BalanceServiceImplTest {
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
    private BalanceService balanceService;

    private Product productA;

    private Shop shop1;


    @BeforeEach
    void setUp() {
        productA = productRepository.save(new Product(null, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null));
        Product productB = productRepository.save(new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null));

        Lot lotA = lotRepository.save(new Lot(null, new Date(1), 500.0, 5.5, productA));
        Lot lotB = lotRepository.save(new Lot(null, new Date(2), 800.0, 8.0, productB));

        shop1 = shopRepository.save(new Shop(null, SHOP1_NAME, SHOP1_ADDRESS, null));
        Shop shop2 = shopRepository.save(new Shop(null, SHOP2_NAME, SHOP2_ADDRESS, null));

        Pack packA1 = packRepository.save(new Pack(null, new Date(3), 200.0, lotA, shop1));
        Pack packA2 = packRepository.save(new Pack(null, new Date(4), 150.0, lotA, shop2));
        Pack packB1 = packRepository.save(new Pack(null, new Date(5), 200.0, lotB, shop1));
        Pack packB2 = packRepository.save(new Pack(null, new Date(6), 300.0, lotB, shop2));

        saleRepository.save(new Sale(null, new Date(3), 50.0, 8.0, packA1));
        saleRepository.save(new Sale(null, new Date(5), 10.0, 9.0, packA2));
        saleRepository.save(new Sale(null, new Date(7), 30.0, 15.0, packB1));
        saleRepository.save(new Sale(null, new Date(9), 90.0, 12.0, packB2));
        saleRepository.save(new Sale(null, new Date(15), 20.0, 8.0, packA1));
    }

    @AfterEach
    void tearDown() {
        SaleServiceImplTest.cleanDatabase(saleRepository, packRepository, lotRepository, productRepository, shopRepository);
    }

    @Test
    void getNetBalance() {
        BalanceDTO balance = balanceService.getNetBalance(null, null);
        assertEquals(1400.0, balance.getSpent());
        assertEquals(2180.0, balance.getIncome());
    }

    @Test
    void getAllProductsBalances() {
        List<BalanceDTO> balances = balanceService.getAllProductsBalances(null, null);
        assertEquals(2, balances.size());
    }

    @Test
    void getAllShopsBalances() {
        List<BalanceDTO> balances = balanceService.getAllShopsBalances(null, null);
        assertEquals(2, balances.size());
    }

    @Test
    void getProductNetBalance() {
        BalanceDTO balance = balanceService.getProductNetBalance(null, null, productA.getId());
        assertEquals(440.0, balance.getSpent());
        assertEquals(650.0, balance.getIncome());
    }

    @Test
    void getShopNetBalance() {
        BalanceDTO balance = balanceService.getShopNetBalance(null, null, shop1.getId());
        assertEquals(625.0, balance.getSpent());
        assertEquals(1010.0, balance.getIncome());
    }

    @Test
    void getProductAllShopsBalances() {
        List<BalanceDTO> balances = balanceService.getProductAllShopsBalances(null, null, productA.getId());
        assertEquals(2, balances.size());
    }

    @Test
    void getShopAllProductsBalances() {
        List<BalanceDTO> balances = balanceService.getShopAllProductsBalances(null, null, shop1.getId());
        assertEquals(2, balances.size());
    }

    @Test
    void getProductShopNetBalance() {
        BalanceDTO balance = balanceService.getProductShopNetBalance(null, null, productA.getId(), shop1.getId());
        assertEquals(385.0, balance.getSpent());
        assertEquals(560.0, balance.getIncome());
    }
}
