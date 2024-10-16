package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static com.i8ai.training.store.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest(showSql = false)
@RequiredArgsConstructor
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
    private OfferRepository offerRepository;

    @Autowired
    private SaleRepository saleRepository;

    private Long idProductA;
    private Long idProductB;

    private Long idShop1;
    private Long idShop2;

    private Long idLotA;

    private Long idPack2B;

    @BeforeEach
    void setUp() {
        Product productA = productRepository.save(
                Product.builder().code(PRODUCT_A_CODE).name(PRODUCT_A_NAME).measure(PRODUCT_A_MEASURE).build()
        );
        Product productB = productRepository.save(
                Product.builder().code(PRODUCT_B_CODE).name(PRODUCT_B_NAME).measure(PRODUCT_B_MEASURE).build()
        );
        idProductA = productA.getId();
        idProductB = productB.getId();

        Shop shop1 = shopRepository.save(Shop.builder().name(SHOP1_NAME).address(SHOP1_ADDRESS).build());
        Shop shop2 = shopRepository.save(Shop.builder().name(SHOP2_NAME).address(SHOP2_ADDRESS).build());
        idShop1 = shop1.getId();
        idShop2 = shop2.getId();

        Lot lotA = lotRepository.save(
                Lot.builder().receivedAt(new Date(5)).amount(LOT_A_AMOUNT).cost(PRODUCT_A_COST).product(productA).build()
        );
        Lot lotB = lotRepository.save(
                Lot.builder().receivedAt(new Date(10)).amount(LOT_B_AMOUNT).cost(PRODUCT_B_COST).product(productB).build()
        );
        idLotA = lotA.getId();

        Pack pack1A = packRepository.save(
                Pack.builder().deliveredAt(new Date(15)).amount(PACK1A_AMOUNT).lot(lotA).shop(shop1).build());
        Pack pack1B = packRepository.save(
                Pack.builder().deliveredAt(new Date(20)).amount(PACK1B_AMOUNT).lot(lotB).shop(shop1).build());
        Pack pack2A = packRepository.save(
                Pack.builder().deliveredAt(new Date(25)).amount(PACK2A_AMOUNT).lot(lotA).shop(shop2).build());
        Pack pack2B = packRepository.save(
                Pack.builder().deliveredAt(new Date(30)).amount(PACK2B_AMOUNT).lot(lotB).shop(shop2).build());
        idPack2B = pack2B.getId();

        offerRepository.save(Offer.builder().createdAt(new Date(10)).price(PRODUCT_A_PRICE + 10).pack(pack1A).build());
        Offer offer1A = offerRepository.save(Offer.builder().createdAt(new Date(15)).price(PRODUCT_A_PRICE).pack(pack1A).build());
        offerRepository.save(Offer.builder().createdAt(new Date(15)).price(PRODUCT_B_PRICE + 10).pack(pack1B).build());
        Offer offer1B = offerRepository.save(Offer.builder().createdAt(new Date(20)).price(PRODUCT_B_PRICE).pack(pack1B).build());
        offerRepository.save(Offer.builder().createdAt(new Date(20)).price(PRODUCT_A_PRICE + 10).pack(pack2A).build());
        Offer offer2A = offerRepository.save(Offer.builder().createdAt(new Date(25)).price(PRODUCT_A_PRICE).pack(pack2A).build());
        offerRepository.save(Offer.builder().createdAt(new Date(25)).price(PRODUCT_B_PRICE + 10).pack(pack2B).build());
        Offer offer2B = offerRepository.save(Offer.builder().createdAt(new Date(30)).price(PRODUCT_B_PRICE).pack(pack2B).build());

        saleRepository.save(Sale.builder().registeredAt(new Date(35)).amount(SALE_1A35_AMOUNT).offer(offer1A).build());
        saleRepository.save(Sale.builder().registeredAt(new Date(40)).amount(SALE_1A40_AMOUNT).offer(offer1A).build());
        saleRepository.save(Sale.builder().registeredAt(new Date(45)).amount(SALE_1B45_AMOUNT).offer(offer1B).build());
        saleRepository.save(Sale.builder().registeredAt(new Date(50)).amount(SALE_1B50_AMOUNT).offer(offer1B).build());
        saleRepository.save(Sale.builder().registeredAt(new Date(55)).amount(SALE_2A55_AMOUNT).offer(offer2A).build());
        saleRepository.save(Sale.builder().registeredAt(new Date(60)).amount(SALE_2A60_AMOUNT).offer(offer2A).build());
        saleRepository.save(Sale.builder().registeredAt(new Date(65)).amount(SALE_2B65_AMOUNT).offer(offer2B).build());
        saleRepository.save(Sale.builder().registeredAt(new Date(70)).amount(SALE_2B70_AMOUNT).offer(offer2B).build());
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
        List<Lot> lots = lotRepository.findAllByReceivedAtBetween(new Date(0), new Date());

        assertEquals(2, lots.size());
    }

    @Test
    void findAllLotsByProductIdAndReceivedBetween() {
        List<Lot> lots = lotRepository.findAllByReceivedAtBetweenAndProductId(new Date(0), new Date(), idProductA);

        assertEquals(1, lots.size());
    }

    @Test
    void getAmountArrivedByProductId() {
        Double amount = lotRepository.getAmountArrivedByProductId(idProductB);

        assertEquals(LOT_B_AMOUNT, amount);
    }

    @Test
    void findAllPacksByDeliveredBetween() {
        List<Pack> packs = packRepository.findAllByDeliveredAtBetween(new Date(0), new Date());

        assertEquals(4, packs.size());
    }

    @Test
    void findAllPacksByDeliveredBetweenAndShopId() {
        List<Pack> packs = packRepository.findAllByDeliveredAtBetweenAndShopId(new Date(0), new Date(), idShop1);

        assertEquals(2, packs.size());
    }

    @Test
    void findAllPacksByDeliveredBetweenAndLotProductId() {
        List<Pack> packs = packRepository.findAllByDeliveredAtBetweenAndLotProductId(new Date(0), new Date(), idProductA);

        assertEquals(2, packs.size());
    }

    @Test
    void findAllPacksByDeliveredBetweenAndLotProductIdAndShopId() {
        List<Pack> packs = packRepository.findAllByDeliveredAtBetweenAndLotProductIdAndShopId(
                new Date(0), new Date(), idProductB, idShop2
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
        List<Sale> sales = saleRepository.findAllByRegisteredAtBetween(new Date(0), new Date());

        assertEquals(8, sales.size());
    }

    @Test
    void findAllSalesByRegisteredBetweenAndPackShopId() {
        List<Sale> sales = saleRepository.findAllByRegisteredAtBetweenAndOfferPackShopId(new Date(0), new Date(), idShop2);

        assertEquals(4, sales.size());
    }

    @Test
    void findAllSalesByRegisteredBetweenAndPackLotProductId() {
        List<Sale> sales = saleRepository.findAllByRegisteredAtBetweenAndOfferPackShopId(new Date(0), new Date(), idProductB);

        assertEquals(4, sales.size());
    }

    @Test
    void findAllSalesByRegisteredBetweenAndPackLotProductIdAndPackShopId() {
        List<Sale> sales = saleRepository.findAllByRegisteredAtBetweenAndOfferPackLotProductIdAndOfferPackShopId(
                new Date(0), new Date(), idProductA, idShop1
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
        Double income = saleRepository.getNetSalesIncome(new Date(0), new Date());

        assertEquals(NET_SALES_INCOME, income);
    }

    @Test
    void getIncomeByProductId() {
        Double income = saleRepository.getIncomeByProductId(new Date(0), new Date(), idProductB);

        assertEquals(PRODUCT_B_INCOME, income);
    }

    @Test
    void getIncomeByShopId() {
        Double income = saleRepository.getIncomeByShopId(new Date(0), new Date(), idShop2);

        assertEquals(SHOP2_INCOME, income);
    }

    @Test
    void getIncomeByProductIdAndShopId() {
        Double income = saleRepository.getIncomeByProductIdAndShopId(new Date(0), new Date(), idProductA, idShop1);

        assertEquals(PACK1A_SALES_INCOME, income);
    }

    @Test
    void getNetSalesExpenses() {
        Double expenses = saleRepository.getNetSalesExpenses(new Date(0), new Date());

        assertEquals(NET_SALES_EXPENSES, expenses);
    }

    @Test
    void getSaleExpensesByProductId() {
        Double expenses = saleRepository.getSaleExpensesByProductId(new Date(0), new Date(), idProductB);

        assertEquals(PRODUCT_B_EXPENSES, expenses);
    }

    @Test
    void getSaleExpensesByShopId() {
        Double expenses = saleRepository.getSaleExpensesByShopId(new Date(0), new Date(), idShop2);

        assertEquals(SHOP2_EXPENSES, expenses);
    }

    @Test
    void getSaleExpensesByProductIdAndShopId() {
        Double expenses = saleRepository.getSaleExpensesByProductIdAndShopId(
                new Date(0), new Date(), idProductA, idShop1
        );

        assertEquals(PACK1A_SALES_EXPENSES, expenses);
    }
}
