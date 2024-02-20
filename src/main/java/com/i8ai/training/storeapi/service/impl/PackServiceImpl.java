package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.error.ElementNotFoundException;
import com.i8ai.training.storeapi.error.NotValidAmountException;
import com.i8ai.training.storeapi.model.Pack;
import com.i8ai.training.storeapi.repository.PackRepository;
import com.i8ai.training.storeapi.service.LotService;
import com.i8ai.training.storeapi.service.PackService;
import com.i8ai.training.storeapi.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PackServiceImpl implements PackService {
    private final LotService lotService;

    private final PackRepository packRepository;

    @Autowired
    public PackServiceImpl(PackRepository packRepository, LotService lotService) {
        this.packRepository = packRepository;
        this.lotService = lotService;
    }

    @Override
    public List<Pack> getPacks(Date start, Date end, Long productId, Long shopId) {
        start = DateTimeUtils.dateOrMin(start);
        end = DateTimeUtils.dateOrMax(end);

        if (productId == null) {
            if (shopId == null) {
                return packRepository.findAllByDeliveredBetween(start, end);
            } else {
                return packRepository.findAllByDeliveredBetweenAndShopId(start, end, shopId);
            }
        }

        if (shopId == null) {
            return packRepository.findAllByDeliveredBetweenAndLotProductId(start, end, productId);
        } else {
            return packRepository.findAllByDeliveredBetweenAndLotProductIdAndShopId(start, end, productId, shopId);
        }
    }

    @Override
    public Pack registerPack(Pack newPack) {
        if (newPack.getAmount() <= 0.0 || newPack.getAmount() > getCurrentLotAmount(newPack.getLot().getId())) {
            throw new NotValidAmountException();
        }
        return packRepository.save(newPack);
    }

    @Override
    public Pack getPack(Long packId) {
        return packRepository.findById(packId).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public void deletePack(Long packId) {
        packRepository.deleteById(packId);
    }

    @Override
    public Double getProductDeliveredAmount(Long productId) {
        return Optional.ofNullable(packRepository.getDeliveredAmountByProductId(productId)).orElse(0.0);
    }

    @Override
    public Double getProductDeliveredToShopAmount(Long productId, Long shopId) {
        return Optional.ofNullable(packRepository.getDeliveredAmountByProductIdAndShopId(productId, shopId)).orElse(0.0);
    }

    private Double getCurrentLotAmount(Long lotId) {
        Double initLotAmount = lotService.getLot(lotId).getAmount();
        Double alreadyDeliveredLotAmount = getProductDeliveredAmountFromLot(lotId);
        return initLotAmount - alreadyDeliveredLotAmount;
    }

    private Double getProductDeliveredAmountFromLot(Long lotId) {
        return Optional.ofNullable(packRepository.getDeliveredAmountByLotId(lotId)).orElse(0.0);
    }
}
