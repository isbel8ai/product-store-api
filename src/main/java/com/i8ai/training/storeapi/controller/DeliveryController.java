package com.i8ai.training.storeapi.controller;

import com.i8ai.training.storeapi.domain.Delivery;
import com.i8ai.training.storeapi.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        return deliveryService.getDeliveries(startDate, endDate, productId, shopId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Delivery registerDelivery(@RequestBody Delivery newDelivery) {
        return deliveryService.registerDelivery(newDelivery);
    }

    @DeleteMapping("/{deliveryId}")
    public void deleteDelivery(@PathVariable Long deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
    }

}
