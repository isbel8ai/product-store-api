package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.error.ElementNotFoundException;
import com.i8ai.training.store.error.NotValidAmountException;
import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.repository.PackRepository;
import com.i8ai.training.store.service.LotService;
import com.i8ai.training.store.service.PackService;
import com.i8ai.training.store.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {

    private final LotService lotService;

    private final PackRepository packRepository;

    @Override
    public Pack registerPack(Pack newPack) {
        if (newPack.getAmount() <= 0.0 || newPack.getAmount() > newPack.getLot().getCurrentAmount()) {
            throw new NotValidAmountException();
        }

        lotService.updateDeliveredAmount(newPack.getLot().getId());

        return packRepository.save(newPack);
    }

    @Override
    public Pack getPack(Long packId) {
        return packRepository.findById(packId).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public Pack getActivePack(Long shopId, Long productId) {
        return packRepository.findAvailableByShopIdAndProductId(shopId, productId)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public List<Pack> getPacks(Long productId, Long shopId, Date start, Date end) {
        start = DateTimeUtils.dateOrMin(start);
        end = DateTimeUtils.dateOrNow(end);

        if (productId == null) {
            if (shopId == null) {
                return packRepository.findAllByDeliveredAtBetween(start, end);
            } else {
                return packRepository.findAllByDeliveredAtBetweenAndShopId(start, end, shopId);
            }
        }

        if (shopId == null) {
            return packRepository.findAllByDeliveredAtBetweenAndLotProductId(start, end, productId);
        } else {
            return packRepository.findAllByDeliveredAtBetweenAndLotProductIdAndShopId(start, end, productId, shopId);
        }
    }

    @Override
    public void updateSoldAmount(Long packId) {
        packRepository.updateSoldAmountById(packId);
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
}
