package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.model.Lot;

import java.util.Date;
import java.util.List;

public interface LotService {
    List<Lot> getLots(Long productId, Date start, Date end);

    Lot registerLot(Lot newLot);

    Lot getLot(Long lotId);

    void deleteLot(Long lotId);

    Double getProductReceivedAmount(Long productId);
}
