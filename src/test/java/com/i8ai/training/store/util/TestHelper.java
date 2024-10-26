package com.i8ai.training.store.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i8ai.training.store.model.*;
import com.i8ai.training.store.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.i8ai.training.store.util.TestConstants.*;

@Component
@RequiredArgsConstructor
public class TestHelper {

    private final ProductRepository productRepository;

    private final ShopRepository shopRepository;

    private final LotRepository lotRepository;

    private final PackRepository packRepository;

    private final OfferRepository offerRepository;

    private final SaleRepository saleRepository;

    public Shop createShop1() {
        return shopRepository.save(Shop.builder().name(SHOP1_NAME).address(SHOP1_ADDRESS).build());
    }

    public Shop createShop2() {
        return shopRepository.save(Shop.builder().name(SHOP2_NAME).address(SHOP2_ADDRESS).build());
    }

    public Product createProductA() {
        return productRepository.save(
                Product.builder().code(PRODUCT_A_CODE).name(PRODUCT_A_NAME).measureUnit(PRODUCT_A_MEASURE).build()
        );
    }

    public Product createProductB() {
        return productRepository.save(
                Product.builder().code(PRODUCT_B_CODE).name(PRODUCT_B_NAME).measureUnit(PRODUCT_B_MEASURE).build()
        );
    }

    public Lot createLotA(Product productA) {
        return lotRepository.save(Lot.builder()
                .product(productA)
                .acquiredAmount(LOT_A_AMOUNT)
                .costPerUnit(PRODUCT_A_COST)
                .receivedAt(new Date(5))
                .build()
        );
    }

    public Lot createLotB(Product productB) {
        return lotRepository.save(Lot.builder()
                .receivedAt(new Date(10))
                .acquiredAmount(LOT_B_AMOUNT)
                .costPerUnit(PRODUCT_B_COST)
                .product(productB)
                .build()
        );
    }

    public Pack createPack1A(Lot lotA, Shop shop1) {
        return packRepository.save(
                Pack.builder().deliveredAt(new Date(15)).receivedAmount(PACK1A_AMOUNT).lot(lotA).shop(shop1).build());
    }

    public Pack createPack1B(Lot lotB, Shop shop1) {
        return packRepository.save(
                Pack.builder().deliveredAt(new Date(20)).receivedAmount(PACK1B_AMOUNT).lot(lotB).shop(shop1).build());
    }

    public Pack createPack2A(Lot lotA, Shop shop2) {
        return packRepository.save(
                Pack.builder().deliveredAt(new Date(25)).receivedAmount(PACK2A_AMOUNT).lot(lotA).shop(shop2).build());
    }

    public Pack createPack2B(Lot lotB, Shop shop2) {
        return packRepository.save(
                Pack.builder().deliveredAt(new Date(30)).receivedAmount(PACK2B_AMOUNT).lot(lotB).shop(shop2).build());
    }

    public Offer createOffer(Pack pack1A, Double productAPrice) {
        return offerRepository.save(Offer.builder().createdAt(new Date()).price(productAPrice).pack(pack1A).build());
    }

    public Sale createSale(Offer offer1A, Double sale1a35Amount) {
        return saleRepository.save(
                Sale.builder().registeredAt(new Date()).amount(sale1a35Amount).offer(offer1A).build()
        );
    }

    public void deleteAllShops() {
        shopRepository.deleteAll();
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public void deleteAllLots() {
        lotRepository.deleteAll();
    }

    public void deleteAllPacks() {
        packRepository.deleteAll();
    }

    public void deleteAllOffers() {
        offerRepository.deleteAll();
    }

    public void deleteAllSales() {
        saleRepository.deleteAll();
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
