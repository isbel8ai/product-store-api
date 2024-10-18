package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Lot;

import java.util.Date;
import java.util.List;

public interface LotService {

    Lot registerLot(Lot newLot);

    Lot getLot(Long lotId);

    List<Lot> getLots(Long productId, Date start, Date end);

    void deleteLot(Long lotId);

    Double getProductReceivedAmount(Long productId);

    void updateDeliveredAmount(Long id);
}
