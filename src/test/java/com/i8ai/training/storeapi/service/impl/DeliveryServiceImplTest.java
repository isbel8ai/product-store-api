package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Delivery;
import com.i8ai.training.storeapi.domain.Lot;
import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.domain.Shop;
import com.i8ai.training.storeapi.repository.DeliveryRepository;
import com.i8ai.training.storeapi.repository.LotRepository;
import com.i8ai.training.storeapi.repository.ProductRepository;
import com.i8ai.training.storeapi.repository.ShopRepository;
import com.i8ai.training.storeapi.service.DeliveryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeliveryServiceImplTest {

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
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryService deliveryService;

    private Product productA;
    private Product productB;

    private Lot lotA;
    private Lot lotB;

    private Shop shop1;
    private Shop shop2;

    private Delivery removableDelivery;


    @BeforeEach
    void setUp() {
        productA = productRepository.save(new Product(null, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null));
        productB = productRepository.save(new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null));
        lotA = lotRepository.save(new Lot(null, new Date(100), 5.5, 500.0, productA));
        lotB = lotRepository.save(new Lot(null, new Date(100), 8.0, 800.0, productB));
        shop1 = shopRepository.save(new Shop(null, SHOP1_NAME, SHOP1_ADDRESS, null));
        shop2 = shopRepository.save(new Shop(null, SHOP2_NAME, SHOP2_ADDRESS, null));
        deliveryRepository.save(new Delivery(null, new Date(5000), 50.0, lotA, shop2));
        deliveryRepository.save(new Delivery(null, new Date(9000), 50.0, lotA, shop2));
        deliveryRepository.save(new Delivery(null, new Date(4000), 100.0, lotB, shop1));
        deliveryRepository.save(new Delivery(null, new Date(6000), 70.0, lotB, shop2));
        removableDelivery = deliveryRepository.save(new Delivery(null, new Date(7000), 150.0, lotA, shop1));
    }

    @AfterEach
    void tearDown() {
        deliveryRepository.deleteAll();
        lotRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void getDeliveriesWithAllFilters() {
        List<Delivery> deliveries = deliveryService.getDeliveries(new Date(3000), new Date(6000), productA.getId(), shop2.getId());
        assertEquals(1, deliveries.size());
    }

    @Test
    void getDeliveriesWithShopFilter() {
        List<Delivery> deliveries = deliveryService.getDeliveries(null, null, null, shop1.getId());
        assertEquals(2, deliveries.size());
    }

    @Test
    void getDeliveriesWithProductFilter() {
        List<Delivery> deliveries = deliveryService.getDeliveries(null, null, productB.getId(), null);
        assertEquals(2, deliveries.size());
    }

    @Test
    void getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getDeliveries(null, null, null, null);
        assertEquals(5, deliveries.size());
    }

    @Test
    void registerDeliveryWithNotValidAmount() {
        Exception e = assertThrows(RuntimeException.class, () ->
                deliveryService.registerDelivery(new Delivery(null, new Date(), 1000.0, lotA, shop1)));
        assertTrue(e.getMessage().contains("enough"));
    }

    @Test
    void registerDelivery() {
        assertNotNull(deliveryService.registerDelivery(new Delivery(null, new Date(), 100.0, lotB, shop1)));
    }

    @Test
    void deleteDelivery() {
        deliveryService.deleteDelivery(removableDelivery.getId());
    }

}
