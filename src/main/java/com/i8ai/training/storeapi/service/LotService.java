package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.domain.Lot;

import java.util.Date;
import java.util.List;

public interface LotService {

    List<Lot> getLots(Date start, Date end, Long productId);

    Lot addLot(Lot newLot);

    void deleteLot(Long lotId);
}
