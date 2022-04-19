package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.model.Lot;
import com.i8ai.training.storeapi.error.ElementNotFoundException;
import com.i8ai.training.storeapi.repository.LotRepository;
import com.i8ai.training.storeapi.service.LotService;
import com.i8ai.training.storeapi.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LotServiceImpl implements LotService {
    private final LotRepository lotRepository;

    @Autowired
    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public List<Lot> getLots(Long productId, Date start, Date end) {
        return productId == null ?
                lotRepository.findAllByReceivedBetween(DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end)) :
                lotRepository.findAllByProductIdAndReceivedBetween(
                        productId,
                        DateTimeUtils.dateOrMin(start),
                        DateTimeUtils.dateOrMax(end)
                );
    }

    @Override
    public Lot registerLot(Lot newLot) {
        return lotRepository.save(newLot);
    }

    @Override
    public Lot getLot(Long lotId) {
        return lotRepository.findById(lotId).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public void deleteLot(Long lotId) {
        lotRepository.deleteById(lotId);
    }

    @Override
    public Double getProductReceivedAmount(Long productId) {
        return Optional.ofNullable(lotRepository.getAmountArrivedByProductId(productId)).orElse(0.0);
    }
}
