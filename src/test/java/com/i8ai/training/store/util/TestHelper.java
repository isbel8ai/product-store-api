package com.i8ai.training.store.util;

import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.i8ai.training.store.util.TestUtils.*;

@Component
@RequiredArgsConstructor
public class TestHelper {

    private final ProductRepository productRepository;

    private final ShopRepository shopRepository;

    private final LotRepository lotRepository;

    private final PackRepository packRepository;

    private final OfferRepository offerRepository;

    private final SaleRepository saleRepository;

    public Product createProductA() {
        return productRepository.save(
                Product.builder().code(PRODUCT_A_CODE).name(PRODUCT_A_NAME).measure(PRODUCT_A_MEASURE).build()
        );
    }

    public Product createProductB() {
        return productRepository.save(
                Product.builder().code(PRODUCT_B_CODE).name(PRODUCT_B_NAME).measure(PRODUCT_B_MEASURE).build()
        );
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }


}
