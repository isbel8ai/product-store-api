package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.error.ElementNotFoundException;
import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.repository.LotRepository;
import com.i8ai.training.store.service.LotService;
import com.i8ai.training.store.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    @Override
    public List<Lot> getLots(Long productId, Date start, Date end) {
        return productId == null ?
                lotRepository.findAllByReceivedAtBetween(DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end)) :
                lotRepository.findAllByReceivedAtBetweenAndProductId(
                        DateTimeUtils.dateOrMin(start),
                        DateTimeUtils.dateOrNow(end),
                        productId
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

    @Override
    public void updateDeliveredAmount(Long lotId) {
        lotRepository.updateDeliveredAmountById(lotId);
    }
}
