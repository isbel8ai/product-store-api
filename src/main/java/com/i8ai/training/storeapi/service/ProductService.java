package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product addProduct(Product newProduct);

    Product getProduct(Long productId);

    Product replaceProduct(Long productId, Product modifiedProduct);

    void deleteProduct(Long productId);

}
