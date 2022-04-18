package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.model.Pack;

import java.util.Date;
import java.util.List;

public interface PackService {
    List<Pack> getPacks(Date start, Date end, Long productId, Long shopId);

    Pack registerPack(Pack newPack);

    Pack getPack(Long packId);

    void deletePack(Long packId);

    Double getProductDeliveredAmount(Long productId);

    Double getProductDeliveredToShopAmount(Long productId, Long shopId);
}
