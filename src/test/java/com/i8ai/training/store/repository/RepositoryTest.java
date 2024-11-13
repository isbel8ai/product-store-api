package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.*;
import com.i8ai.training.store.util.TestHelper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.i8ai.training.store.util.DateTimeUtils.MAX_DATETIME;
import static com.i8ai.training.store.util.DateTimeUtils.MIN_DATETIME;
import static com.i8ai.training.store.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Import(TestHelper.class)
@DataJpaTest(showSql = false)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class RepositoryTest {

    private final ProductRepository productRepository;

    private final ShopRepository shopRepository;

    private final LotRepository lotRepository;

    private final PackRepository packRepository;

    private final OfferRepository offerRepository;

    private final SaleRepository saleRepository;

    private final TestHelper helper;

    private Long idProductA;
    private Long idProductB;

    private Long idShop1;
    private Long idShop2;

    private Long idLotA;

    private Long idPack2B;

    @BeforeEach
    void setUp() {
        Product productA = helper.createProductA();
        Product productB = helper.createProductB();
        idProductA = productA.getId();
        idProductB = productB.getId();

        Shop shop1 = helper.createShop1();
        Shop shop2 = helper.createShop2();
        idShop1 = shop1.getId();
        idShop2 = shop2.getId();

        Lot lotA = helper.createLotA(productA);
        Lot lotB = helper.createLotB(productB);
        idLotA = lotA.getId();

        Pack pack1A = helper.createPack1A(lotA, shop1);
        Pack pack1B = helper.createPack1B(lotB, shop1);
        Pack pack2A = helper.createPack2A(lotA, shop2);
        Pack pack2B = helper.createPack2B(lotB, shop2);
        idPack2B = pack2B.getId();

        helper.createOffer(pack1A, PRODUCT_A_PRICE + 10);
        Offer offer1A = helper.createOffer(pack1A, PRODUCT_A_PRICE);
        helper.createOffer(pack1B, PRODUCT_B_PRICE + 10);
        Offer offer1B = helper.createOffer(pack1B, PRODUCT_B_PRICE);
        helper.createOffer(pack2A, PRODUCT_A_PRICE + 10);
        Offer offer2A = helper.createOffer(pack2A, PRODUCT_A_PRICE);
        helper.createOffer(pack2B, PRODUCT_B_PRICE + 10);
        Offer offer2B = helper.createOffer(pack2B, PRODUCT_B_PRICE);

        helper.createSale(offer1A, SALE_1A35_AMOUNT);
        helper.createSale(offer1A, SALE_1A40_AMOUNT);
        helper.createSale(offer1B, SALE_1B45_AMOUNT);
        helper.createSale(offer1B, SALE_1B50_AMOUNT);
        helper.createSale(offer2A, SALE_2A55_AMOUNT);
        helper.createSale(offer2A, SALE_2A60_AMOUNT);
        helper.createSale(offer2B, SALE_2B65_AMOUNT);
        helper.createSale(offer2B, SALE_2B70_AMOUNT);
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
    void findAllLotsByReceivedBetween() {
        List<Lot> lots = lotRepository.findAllByAcquiredAtBetween(MIN_DATETIME, MAX_DATETIME);

        assertEquals(2, lots.size());
    }

    @Test
    void findAllLotsByProductIdAndReceivedBetween() {
        List<Lot> lots = lotRepository.
                findAllByAcquiredAtBetweenAndProductId(MIN_DATETIME, MAX_DATETIME, idProductA);

        assertEquals(1, lots.size());
    }

    @Test
    void getAmountArrivedByProductId() {
        Double amount = lotRepository.getAmountArrivedByProductId(idProductB);

        assertEquals(LOT_B_AMOUNT, amount);
    }

    @Test
    void findAllPacksByDeliveredBetween() {
        List<Pack> packs = packRepository.findAllByReceivedAtBetween(MIN_DATETIME, MAX_DATETIME);

        assertEquals(4, packs.size());
    }

    @Test
    void findAllPacksByDeliveredBetweenAndShopId() {
        List<Pack> packs = packRepository
                .findAllByReceivedAtBetweenAndShopId(MIN_DATETIME, MAX_DATETIME, idShop1);

        assertEquals(2, packs.size());
    }

    @Test
    void findAllPacksByDeliveredBetweenAndLotProductId() {
        List<Pack> packs = packRepository
                .findAllByReceivedAtBetweenAndLotProductId(MIN_DATETIME, MAX_DATETIME, idProductA);

        assertEquals(2, packs.size());
    }

    @Test
    void findAllPacksByDeliveredBetweenAndLotProductIdAndShopId() {
        List<Pack> packs = packRepository.findAllByReceivedAtBetweenAndLotProductIdAndShopId(
                MIN_DATETIME, MAX_DATETIME, idProductB, idShop2
        );

        assertEquals(1, packs.size());
    }

    @Test
    void getDeliveredAmountByLotId() {
        Double amount = packRepository.getDeliveredAmountByLotId(idLotA);

        assertEquals(PACK1A_AMOUNT + PACK2A_AMOUNT, amount);
    }

    @Test
    void getDeliveredAmountByProductId() {
        Double amount = packRepository.getDeliveredAmountByProductId(idProductB);

        assertEquals(PACK1B_AMOUNT + PACK2B_AMOUNT, amount);
    }

    @Test
    void getDeliveredAmountByProductIdAndShopId() {
        Double amount = packRepository.getDeliveredAmountByProductIdAndShopId(idProductA, idShop1);

        assertEquals(PACK1A_AMOUNT, amount);
    }


    @Test
    void findAllSalesByRegisteredBetween() {
        List<Sale> sales = saleRepository.findAllByRegisteredAtBetween(MIN_DATETIME, MAX_DATETIME);

        assertEquals(8, sales.size());
    }

    @Test
    void findAllSalesByRegisteredBetweenAndPackShopId() {
        List<Sale> sales = saleRepository
                .findAllByRegisteredAtBetweenAndOfferPackShopId(MIN_DATETIME, MAX_DATETIME, idShop2);

        assertEquals(4, sales.size());
    }

    @Test
    void findAllSalesByRegisteredBetweenAndPackLotProductId() {
        List<Sale> sales = saleRepository
                .findAllByRegisteredAtBetweenAndOfferPackShopId(MIN_DATETIME, MAX_DATETIME, idProductB);

        assertEquals(4, sales.size());
    }

    @Test
    void findAllSalesByRegisteredBetweenAndPackLotProductIdAndPackShopId() {
        List<Sale> sales = saleRepository.findAllByRegisteredAtBetweenAndOfferPackLotProductIdAndOfferPackShopId(
                MIN_DATETIME, MAX_DATETIME, idProductA, idShop1
        );

        assertEquals(2, sales.size());
    }

    @Test
    void getSoldAmountByPackId() {
        Double amount = saleRepository.getSoldAmountByPackId(idPack2B);

        assertEquals(PACK2B_SALES_AMOUNT, amount);
    }

    @Test
    void getSoldAmountByProductIdAndShopId() {
        Double amount = saleRepository.getSoldAmountByProductIdAndShopId(idProductA, idShop1);

        assertEquals(PACK1A_SALES_AMOUNT, amount);
    }

    @Test
    void getNetSalesIncome() {
        Double income = saleRepository.getNetSalesIncome(MIN_DATETIME, MAX_DATETIME);

        assertEquals(NET_SALES_INCOME, income);
    }

    @Test
    void getIncomeByProductId() {
        Double income = saleRepository.getIncomeByProductId(MIN_DATETIME, MAX_DATETIME, idProductB);

        assertEquals(PRODUCT_B_INCOME, income);
    }

    @Test
    void getIncomeByShopId() {
        Double income = saleRepository.getIncomeByShopId(MIN_DATETIME, MAX_DATETIME, idShop2);

        assertEquals(SHOP2_INCOME, income);
    }

    @Test
    void getIncomeByProductIdAndShopId() {
        Double income = saleRepository
                .getIncomeByProductIdAndShopId(MIN_DATETIME, MAX_DATETIME, idProductA, idShop1);

        assertEquals(PACK1A_SALES_INCOME, income);
    }

    @Test
    void getNetSalesExpenses() {
        Double expenses = saleRepository.getNetSalesExpenses(MIN_DATETIME, MAX_DATETIME);

        assertEquals(NET_SALES_EXPENSES, expenses);
    }

    @Test
    void getSaleExpensesByProductId() {
        Double expenses = saleRepository.getSaleExpensesByProductId(MIN_DATETIME, MAX_DATETIME, idProductB);

        assertEquals(PRODUCT_B_EXPENSES, expenses);
    }

    @Test
    void getSaleExpensesByShopId() {
        Double expenses = saleRepository.getSaleExpensesByShopId(MIN_DATETIME, MAX_DATETIME, idShop2);

        assertEquals(SHOP2_EXPENSES, expenses);
    }

    @Test
    void getSaleExpensesByProductIdAndShopId() {
        Double expenses = saleRepository.getSaleExpensesByProductIdAndShopId(
                MIN_DATETIME, MAX_DATETIME, idProductA, idShop1
        );

        assertEquals(PACK1A_SALES_EXPENSES, expenses);
    }
}
