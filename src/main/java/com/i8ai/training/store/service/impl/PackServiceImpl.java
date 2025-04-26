package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.exception.ElementNotFoundException;
import com.i8ai.training.store.exception.InvalidPackAmountException;
import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.repository.PackRepository;
import com.i8ai.training.store.rest.dto.PackDto;
import com.i8ai.training.store.service.LotService;
import com.i8ai.training.store.service.PackService;
import com.i8ai.training.store.service.ShopService;
import com.i8ai.training.store.util.DateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {

    private final LotService lotService;

    private final ShopService shopService;

    private final PackRepository packRepository;

    @Override
    @Transactional
    public Pack registerPack(PackDto packDto) {
        Lot lot = lotService.getLot(packDto.lotId());
        if (packDto.amount() <= 0.0 || packDto.amount() > lot.getCurrentAmount()) {
            throw new InvalidPackAmountException(packDto.amount(), lot);
        }

        Shop shop = shopService.getShop(packDto.shopId());
        Pack pack = Pack.builder()
                .lot(lot)
                .shop(shop)
                .receivedAmount(packDto.amount())
                .receivedAt(DateTimeUtils.dateTimeOrNow(packDto.receivedAt()))
                .build();

        packRepository.save(pack);
        lotService.updateDeliveredAmount(lot.getId());
        return pack;
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
    public List<Pack> getPacks(Long productId, Long shopId, LocalDateTime start, LocalDateTime end) {
        start = DateTimeUtils.dateTimeOrMin(start);
        end = DateTimeUtils.dateTimeOrMax(end);

        if (productId == null) {
            if (shopId == null) {
                return packRepository.findAllByReceivedAtBetween(start, end);
            } else {
                return packRepository.findAllByReceivedAtBetweenAndShopId(start, end, shopId);
            }
        }

        if (shopId == null) {
            return packRepository.findAllByReceivedAtBetweenAndLotProductId(start, end, productId);
        } else {
            return packRepository.findAllByReceivedAtBetweenAndLotProductIdAndShopId(start, end, productId, shopId);
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
