package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.service.data.Existence;

import java.util.List;

public interface ExistenceService {
    List<Existence> getAllProductsExistenceInMain();

    Existence getProductExistenceInMain(Long productId);

    List<Existence> getProductExistenceInAllShops(Long productId);

    Existence getProductExistenceInShop(Long productId, Long shopId);
}
