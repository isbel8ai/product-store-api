package com.i8ai.training.storeapi.repository;

import com.i8ai.training.storeapi.util.DateTimeUtils;
import static com.i8ai.training.storeapi.util.TestUtils.*;
import com.i8ai.training.storeapi.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class RepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private SaleRepository saleRepository;

    @BeforeEach
    void setUp() {
        Product productA = productRepository.save(PRODUCT_A);
        Product productB = productRepository.save(PRODUCT_B);

        Shop shop1 = shopRepository.save(SHOP1);
        Shop shop2 = shopRepository.save(SHOP2);

        Lot lotA = lotRepository.save(new Lot(LOT_A_ID, new Date(5), LOT_A_AMOUNT, PRODUCT_A_COST, productA));
        Lot lotB = lotRepository.save(new Lot(LOT_B_ID, new Date(10), LOT_B_AMOUNT, PRODUCT_B_COST, productB));

        Pack pack1A = packRepository.save(new Pack(PACK1A_ID, new Date(15), PACK1A_AMOUNT, lotA, shop1));
        Pack pack1B = packRepository.save(new Pack(PACK1B_ID, new Date(25), PACK1B_AMOUNT, lotB, shop1));
        Pack pack2A = packRepository.save(new Pack(PACK2A_ID, new Date(20), PACK2A_AMOUNT, lotA, shop2));
        Pack pack2B = packRepository.save(new Pack(PACK2B_ID, new Date(30), PACK2B_AMOUNT, lotB, shop2));

        saleRepository.save(new Sale(null, new Date(35), SALE_1A35_AMOUNT, PRODUCT_A_PRICE, pack1A));
        saleRepository.save(new Sale(null, new Date(40), SALE_1A40_AMOUNT, PRODUCT_A_PRICE, pack1A));
        saleRepository.save(new Sale(null, new Date(45), SALE_1B45_AMOUNT, PRODUCT_B_PRICE, pack1B));
        saleRepository.save(new Sale(null, new Date(50), SALE_1B50_AMOUNT, PRODUCT_B_PRICE, pack1B));
        saleRepository.save(new Sale(null, new Date(55), SALE_2A55_AMOUNT, PRODUCT_A_PRICE, pack2A));
        saleRepository.save(new Sale(null, new Date(60), SALE_2A60_AMOUNT, PRODUCT_A_PRICE, pack2A));
        saleRepository.save(new Sale(null, new Date(65), SALE_2B65_AMOUNT, PRODUCT_B_PRICE, pack2B));
        saleRepository.save(new Sale(null, new Date(70), SALE_2B70_AMOUNT, PRODUCT_B_PRICE, pack2B));
    }

    @AfterEach
    void tearDown() {
        saleRepository.deleteAll();
        packRepository.deleteAll();
        lotRepository.deleteAll();
        productRepository.deleteAll();
        shopRepository.deleteAll();
    }

    @Test
    void findAllByReceivedBetween() {
    }

    @Test
    void findAllByReceivedBetweenAndProductId() {
    }

    @Test
    void getAmountArrivedByProductId() {
    }

    @Test
    void findAllByRegisteredBetween() {
    }

    @Test
    void findAllByRegisteredBetweenAndPackShopId() {
    }

    @Test
    void findAllByRegisteredBetweenAndPackLotProductId() {
    }

    @Test
    void findAllByRegisteredBetweenAndPackLotProductIdAndPackShopId() {
    }

    @Test
    void getRemainingAmountByPackId() {

    }

    @Test
    void getSoldAmountByProductIdAndShopId() {
    }

    @Test
    void getNetIncome() {
        Double result = saleRepository.getNetSalesIncome(DateTimeUtils.DATE_MIN, DateTimeUtils.DATE_MAX).orElse(0.0);
        assertEquals(NET_SALES_INCOME, result);
    }

    @Test
    void getIncomeByProductId() {
    }

    @Test
    void getIncomeByShopId() {
    }

    @Test
    void getIncomeByProductIdAndShopId() {
    }

    @Test
    void getSaleExpensesByProductId() {
    }

    @Test
    void getSaleExpensesByShopId() {
    }

    @Test
    void getSaleExpensesByProductIdAndShopId() {
    }
}