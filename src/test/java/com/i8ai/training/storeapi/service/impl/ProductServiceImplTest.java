package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Product;
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
    private static final String PRODUCT_A_NAME = "a_product_name";
    private static final String PRODUCT_B_NAME = "b_product_name";
    private static final String PRODUCT_A_MEASURE = "a_product_measure";
    private static final String PRODUCT_B_MEASURE = "b_product_measure";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product createdProduct;

    @BeforeEach
    void setUp() {
        createdProduct = productRepository.save(new Product(null, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null));
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void getAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
    }

    @Test
    void addProductWithExistingCode() {
        Exception e = assertThrows(RuntimeException.class, () ->
                productService.addProduct(new Product(null, PRODUCT_A_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null)));
        assertTrue(e.getMessage().contains("already exist"));
    }

    @Test
    void addProduct() {
        assertNotNull(productService.addProduct(new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null)));
    }

    @Test
    void getProduct() {
        assertNotNull(productService.getProduct(createdProduct.getId()));
    }

    @Test
    void replaceProduct() {
        assertNotNull(productService.replaceProduct(createdProduct.getId(), new Product(null, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null)));
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(createdProduct.getId());
    }

}
