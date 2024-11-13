package com.i8ai.training.store.rest;

import com.i8ai.training.store.rest.dto.OfferDto;
import com.i8ai.training.store.service.OfferService;
import com.i8ai.training.store.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OfferDto createOffer(@RequestBody OfferDto offerDto) {
        return new OfferDto(offerService.createOffer(offerDto));
    }

    @GetMapping
    public List<OfferDto> getCurrentOffers(@RequestParam(required = false) Long shopId,
                                           @RequestParam(required = false) Long productId) {
        return offerService.getCurrentOffers(shopId, productId).stream().map(OfferDto::new).toList();
    }

    @GetMapping("history")
    public List<OfferDto> getOffersHistory(@RequestParam(required = false) Long shopId,
                                           @RequestParam(required = false) Long productId,
                                           @RequestParam(required = false) ZonedDateTime start,
                                           @RequestParam(required = false) ZonedDateTime end) {
        return offerService.getOffersHistory(
                shopId, productId, DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end)
        ).stream().map(OfferDto::new).toList();
    }
}
