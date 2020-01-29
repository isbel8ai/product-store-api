package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Delivery;
import com.i8ai.training.storeapi.repository.DeliveryRepository;
import com.i8ai.training.storeapi.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public List<Delivery> getDeliveries(Date start, Date end, Long productId, Long shopId) {
        if (start == null) start = new Date(0);
        if (end == null) end = new Date();

        if (productId == null) {
            if(shopId == null) {
                return deliveryRepository.findAllByDeliveredBetween(start, end);
            } else {
                return deliveryRepository.findAllByDeliveredBetweenAndToId(start, end, shopId);
            }
        }

        if(shopId == null) {
            return deliveryRepository.findAllByDeliveredBetweenAndFromProductId(start, end, productId);
        } else {
            return deliveryRepository.findAllByDeliveredBetweenAndFromProductIdAndToId(start, end, productId, shopId);
        }
    }

    @Override
    public Delivery registerDelivery(Delivery newDelivery) {
        return deliveryRepository.save(newDelivery);
    }

    @Override
    public void deleteDelivery(Long deliveryId) {
        deliveryRepository.deleteById(deliveryId);
    }
}
