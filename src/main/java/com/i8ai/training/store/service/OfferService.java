package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.rest.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.List;

public interface OfferService {

    Offer createOffer(OfferDto offerDto);

    Offer getOffer(Long offerId);

    List<Offer> getCurrentOffers(Long shopId, Long productId);

    List<Offer> getOffersHistory(Long shopId, Long productId, LocalDateTime start, LocalDateTime end);
}
