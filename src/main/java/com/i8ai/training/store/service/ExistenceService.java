package com.i8ai.training.store.service;

import com.i8ai.training.store.rest.dto.ExistenceDto;

import java.util.List;

public interface ExistenceService {

    List<ExistenceDto> getAllProductsExistenceInMain();

    ExistenceDto getProductExistenceInMain(Long productId);

    List<ExistenceDto> getProductExistenceInAllShops(Long productId);

    ExistenceDto getProductExistenceInShop(Long productId, Long shopId);
}
