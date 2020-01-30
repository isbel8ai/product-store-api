package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.domain.Pack;

import java.util.Date;
import java.util.List;

public interface PackService {
    List<Pack> getPacks(Date start, Date end, Long productId, Long shopId);

    Pack registerPack(Pack newPack);

    Pack getPack(Long packId);

    void deletePack(Long packId);
}
