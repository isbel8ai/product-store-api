package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Delivery;
import com.i8ai.training.storeapi.repository.DeliveryRepository;
import com.i8ai.training.storeapi.service.DeliveryService;
import com.i8ai.training.storeapi.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final LotService lotService;

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, LotService lotService) {
        this.deliveryRepository = deliveryRepository;
        this.lotService = lotService;
    }

    @Override
    public List<Delivery> getDeliveries(Date start, Date end, Long productId, Long shopId) {
        if (start == null) start = new Date(0);
        if (end == null) end = new Date();

        if (productId == null) {
            if (shopId == null) {
                return deliveryRepository.findAllByDeliveredBetween(start, end);
            } else {
                return deliveryRepository.findAllByDeliveredBetweenAndShopId(start, end, shopId);
            }
        }

        if (shopId == null) {
            return deliveryRepository.findAllByDeliveredBetweenAndLotProductId(start, end, productId);
        } else {
            return deliveryRepository.findAllByDeliveredBetweenAndLotProductIdAndShopId(start, end, productId, shopId);
        }
    }

    private Double getCurrentLotAmount(Long lotId) {
        Double initLotAmount = lotService.getLot(lotId).getAmount();
        Double alreadyDeliveredLotAmount = deliveryRepository.getDeliveredAmountByLotId(lotId);
        return initLotAmount - alreadyDeliveredLotAmount;
    }

    @Override
    public Delivery registerDelivery(Delivery newDelivery) {
        if (newDelivery.getAmount() > getCurrentLotAmount(newDelivery.getLot().getId())) {
            throw new RuntimeException("Not enough amount in lot shop make the delivery");
        }
        return deliveryRepository.save(newDelivery);
    }

    @Override
    public void deleteDelivery(Long deliveryId) {
        deliveryRepository.deleteById(deliveryId);
    }
}
