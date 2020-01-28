package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.repository.ProductRepository;
import com.i8ai.training.storeapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    @Override
    public Product replaceProduct(Long productId, Product modifiedProduct) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setCode(modifiedProduct.getCode());
        product.setName(modifiedProduct.getName());
        product.setMeasure(modifiedProduct.getMeasure());
        product.setDescription(modifiedProduct.getDescription());
        productRepository.save(product);
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
