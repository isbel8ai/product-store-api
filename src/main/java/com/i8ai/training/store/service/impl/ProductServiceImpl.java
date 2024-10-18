package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.error.ElementNotFoundException;
import com.i8ai.training.store.error.NotValidElementDataException;
import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.repository.ProductRepository;
import com.i8ai.training.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product newProduct) {
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
        Product product = productRepository.findById(productId).orElseThrow(ElementNotFoundException::new);
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
