package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product newProduct);

    Product getProduct(Long productId);

    List<Product> getAllProducts();

    Product replaceProduct(Long productId, Product modifiedProduct);

    void deleteProduct(Long productId);

}
