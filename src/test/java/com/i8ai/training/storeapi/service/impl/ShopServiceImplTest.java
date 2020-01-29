package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Shop;
import com.i8ai.training.storeapi.repository.ShopRepository;
import com.i8ai.training.storeapi.service.ShopService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

@SpringBootTest
class ShopServiceImplTest {
    private static final String SHOP1_NAME = "name of shop 1";
    private static final String SHOP1_ADDRESS = "address of shop 1";
    private static final String SHOP2_NAME = "name of shop 2";
    private static final String SHOP2_ADDRESS = "address of shop 2";

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    private Shop createdShop;

    @BeforeEach
    void setUp() {
        createdShop = shopRepository.save(new Shop(null, SHOP1_NAME, SHOP1_ADDRESS, null));
    }

    @AfterEach
    void tearDown() {
        shopRepository.deleteAll();
    }

    @Test
    void getAllShops() {
        List<Shop> shops = shopService.getAllShops();
        assertEquals(1, shops.size());
    }

    @Test
    void addShop() {
        assertNotNull(shopService.addShop(new Shop(null, SHOP2_NAME, SHOP2_ADDRESS, null)));
    }

    @Test
    void getShop() {
        assertNotNull(shopService.getShop(createdShop.getId()));
    }

    @Test
    void replaceShop() {
        assertNotNull(shopService.replaceShop(createdShop.getId(), new Shop(null, SHOP2_NAME, SHOP2_ADDRESS, null)));
    }

    @Test
    void deleteShop() {
        shopService.deleteShop(createdShop.getId());
    }
}
