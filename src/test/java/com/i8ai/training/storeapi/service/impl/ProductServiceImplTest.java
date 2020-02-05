package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.exception.NotValidElementDataException;
import com.i8ai.training.storeapi.repository.ProductRepository;
import com.i8ai.training.storeapi.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {
    private static final String PRODUCT_A_CODE = "a_product_code";
    private static final String PRODUCT_B_CODE = "b_product_code";
    private static final String PRODUCT_C_CODE = "c_product_code";
    private static final String PRODUCT_A_NAME = "a_product_name";
    private static final String PRODUCT_B_NAME = "b_product_name";
    private static final String PRODUCT_C_NAME = "c_product_name";
    private static final String PRODUCT_A_MEASURE = "a_product_measure";
    private static final String PRODUCT_B_MEASURE = "b_product_measure";
    private static final String PRODUCT_C_MEASURE = "c_product_measure";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product productA;
    private Product productB;

    @BeforeEach
    void setUp() {
        productA = productRepository.save(new Product(null, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null));
        productB = productRepository.save(new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null));
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void getAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    void addProductWithExistingCode() {
        assertThrows(NotValidElementDataException.class, () ->
                productService.addProduct(new Product(null, PRODUCT_A_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null))
        );

    }

    @Test
    void addProduct() {
        assertDoesNotThrow(() ->
                productService.addProduct(new Product(null, PRODUCT_C_CODE, PRODUCT_C_NAME, PRODUCT_C_MEASURE, null))
        );
    }

    @Test
    void getProduct() {
        assertDoesNotThrow(() ->
                productService.getProduct(productA.getId())
        );
    }

    @Test
    void replaceProductWithExistingCode() {
        assertThrows(NotValidElementDataException.class, () ->
                productService.replaceProduct(productA.getId(), new Product(null, PRODUCT_B_CODE, PRODUCT_C_NAME, PRODUCT_C_MEASURE, null))
        );
    }

    @Test
    void replaceProduct() {
        assertDoesNotThrow(() ->
                productService.replaceProduct(productA.getId(), new Product(null, PRODUCT_C_CODE, PRODUCT_C_NAME, PRODUCT_C_MEASURE, null))
        );
    }

    @Test
    void deleteProduct() {
        assertDoesNotThrow(() ->
                productService.deleteProduct(productB.getId())
        );
    }
}
