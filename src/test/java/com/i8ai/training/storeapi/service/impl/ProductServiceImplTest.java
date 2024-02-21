package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.error.ElementNotFoundException;
import com.i8ai.training.storeapi.error.NotValidElementDataException;
import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private static final Product PRODUCT_A = new Product(PRODUCT_A_ID, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null);
    private static final Product PRODUCT_B = new Product(PRODUCT_B_ID, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null);

    @Mock
    private ProductRepository productRepositoryMock;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProducts() {
        when(productRepositoryMock.findAll()).thenReturn(List.of(PRODUCT_A, PRODUCT_B));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
    }

    @Test
    void addProductWithExistingCode() {
        when(productRepositoryMock.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(NotValidElementDataException.class, () -> productService.addProduct(PRODUCT_A));
    }

    @Test
    void addProduct() {
        assertDoesNotThrow(() -> productService.addProduct(PRODUCT_B));
    }

    @Test
    void getProduct() {
        when(productRepositoryMock.findById(PRODUCT_A_ID)).thenReturn(Optional.of(PRODUCT_A));

        Product product = productService.getProduct(PRODUCT_A_ID);

        assertEquals(PRODUCT_A_NAME, product.getName());
    }

    @Test
    void replaceNotExistingProduct() {
        when(productRepositoryMock.findById(PRODUCT_A_ID)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> productService.replaceProduct(PRODUCT_A_ID, PRODUCT_B));
    }

    @Test
    void replaceProductWithExistingCode() {
        when(productRepositoryMock.findById(PRODUCT_A_ID)).thenReturn(Optional.of(mock(Product.class)));
        when(productRepositoryMock.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(NotValidElementDataException.class, () -> productService.replaceProduct(PRODUCT_A_ID, PRODUCT_B));
    }

    @Test
    void replaceProduct() {
        when(productRepositoryMock.findById(PRODUCT_A_ID)).thenReturn(Optional.of(mock(Product.class)));

        assertDoesNotThrow(() -> productService.replaceProduct(PRODUCT_A_ID, PRODUCT_B));
    }

    @Test
    void deleteProduct() {
        assertDoesNotThrow(() -> productService.deleteProduct(PRODUCT_B_ID));
    }
}
