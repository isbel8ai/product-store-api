package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.rest.dto.OfferDto;
import com.i8ai.training.store.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Offer createOffer(@RequestBody OfferDto offerDto) {
        return offerService.createOffer(offerDto);
    }

    @GetMapping
    public List<Offer> getCurrentOffers(@RequestParam(required = false) Long shopId,
                                        @RequestParam(required = false) Long productId) {
        return offerService.getCurrentOffers(shopId, productId);
    }

    @GetMapping("history")
    public List<Offer> getOffersHistory(@RequestParam(required = false) Long shopId,
                                        @RequestParam(required = false) Long productId,
                                        @RequestParam(required = false) Date start,
                                        @RequestParam(required = false) Date end) {
        return offerService.getOffersHistory(shopId, productId, start, end);
    }
}
