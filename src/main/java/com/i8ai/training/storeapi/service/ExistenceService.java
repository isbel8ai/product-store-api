package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.rest.dto.ExistenceDTO;

import java.util.List;

public interface ExistenceService {
    List<ExistenceDTO> getAllProductsExistenceInMain();

    ExistenceDTO getProductExistenceInMain(Long productId);

    List<ExistenceDTO> getProductExistenceInAllShops(Long productId);

    ExistenceDTO getProductExistenceInShop(Long productId, Long shopId);
}
