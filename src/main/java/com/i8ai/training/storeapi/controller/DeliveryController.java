package com.i8ai.training.storeapi.controller;

import com.i8ai.training.storeapi.domain.Delivery;
import com.i8ai.training.storeapi.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<Delivery> getDeliveries(@RequestParam(required = false) Date startDate,
                                        @RequestParam(required = false) Date endDate,
                                        @RequestParam(required = false) Long productId,
                                        @RequestParam(required = false) Long shopId) {
        return deliveryService.getDeliveries(startDate, endDate, productId,shopId);
    }
}
