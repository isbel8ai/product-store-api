package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Pack;

import java.util.Date;
import java.util.List;

public interface PackService {
    List<Pack> getPacks(Long productId, Long shopId, Date start, Date end);

    Pack registerPack(Pack newPack);

    Pack getPack(Long packId);

    void deletePack(Long packId);

    Double getProductDeliveredAmount(Long productId);

    Double getProductDeliveredToShopAmount(Long productId, Long shopId);
}
