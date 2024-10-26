package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.rest.dto.PackDto;

import java.util.Date;
import java.util.List;

public interface PackService {

    Pack registerPack(PackDto packDto);

    Pack getPack(Long packId);

    Pack getActivePack(Long shopId, Long productId);

    List<Pack> getPacks(Long productId, Long shopId, Date start, Date end);

    void updateSoldAmount(Long packId);

    void deletePack(Long packId);

    Double getProductDeliveredAmount(Long productId);

    Double getProductDeliveredToShopAmount(Long productId, Long shopId);
}
