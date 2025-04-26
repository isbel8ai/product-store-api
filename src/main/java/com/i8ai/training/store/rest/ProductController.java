package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product newProduct) {
        return productService.createProduct(newProduct);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{productId}")
    public Product getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping(value = "{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product replaceProduct(@PathVariable Long productId, @Valid @RequestBody Product modifiedProduct) {
        return productService.replaceProduct(productId, modifiedProduct);
    }

    @DeleteMapping("{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
