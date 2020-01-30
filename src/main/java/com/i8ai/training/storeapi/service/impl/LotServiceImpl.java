package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Lot;
import com.i8ai.training.storeapi.repository.LotRepository;
import com.i8ai.training.storeapi.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    @Autowired
    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public List<Lot> getLots(Date start, Date end, Long productId) {
        if (start == null) start = new Date(0);
        if (end == null) end = new Date();

        return productId == null ?
                lotRepository.findAllByReceivedBetween(start, end) :
                lotRepository.findAllByReceivedBetweenAndProductId(start, end, productId);
    }

    @Override
    public Lot registerLot(Lot newLot) {
        return lotRepository.save(newLot);
    }

    @Override
    public void deleteLot(Long lotId) {
        lotRepository.deleteById(lotId);
    }

    @Override
    public Double getProductReceivedAmount(Long productId) {
        return lotRepository.getAmountArrivedByProductId(productId);
    }

}
