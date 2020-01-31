package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.service.dto.ExistenceDTO;

public interface ExistenceService {
    ExistenceDTO getProductExistence(Long productId, Long shopId);
}
