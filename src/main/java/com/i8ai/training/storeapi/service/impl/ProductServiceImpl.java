package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.exception.ElementNotFoundException;
import com.i8ai.training.storeapi.exception.NotValidElementDataException;
import com.i8ai.training.storeapi.repository.ProductRepository;
import com.i8ai.training.storeapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return productRepository.save(newProduct);
        } catch (DataIntegrityViolationException e) {
            throw new NotValidElementDataException();
        }
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public Product replaceProduct(Long productId, Product modifiedProduct) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setCode(modifiedProduct.getCode());
        product.setName(modifiedProduct.getName());
        product.setMeasure(modifiedProduct.getMeasure());
        product.setDescription(modifiedProduct.getDescription());
        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new NotValidElementDataException();
        }
        return product;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

}
