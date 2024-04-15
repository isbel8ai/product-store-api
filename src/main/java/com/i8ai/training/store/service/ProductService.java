package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product addProduct(Product newProduct);

    Product getProduct(Long productId);

    Product replaceProduct(Long productId, Product modifiedProduct);

    void deleteProduct(Long productId);

}
