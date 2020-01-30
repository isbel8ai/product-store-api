package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.domain.Delivery;

import java.util.Date;
import java.util.List;

public interface DeliveryService {

    List<Delivery> getDeliveries(Date start, Date end, Long productId, Long shopId);

    Delivery registerDelivery(Delivery newDelivery);

    void deleteDelivery(Long deliveryId);

}
