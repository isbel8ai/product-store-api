package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.repository.OfferRepository;
import com.i8ai.training.store.rest.dto.OfferDto;
import com.i8ai.training.store.service.PackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.i8ai.training.store.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepositoryMock;

    @Mock
    private PackService packServiceMock;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Test
    void createOffer() {
        when(packServiceMock.getActivePack(SHOP1_ID, PRODUCT_A_ID)).thenReturn(PACK1A);

        offerService.createOffer(new OfferDto(OFFER1A));

        verify(offerRepositoryMock).save(notNull());
    }

    @Test
    void getOffer() {
        when(offerRepositoryMock.findById(OFFER1B_ID)).thenReturn(Optional.of(OFFER1B));

        Offer offer = offerService.getOffer(OFFER1B_ID);

        assertEquals(OFFER1B_ID, offer.getId());
    }

    @Test
    void getCurrentOffers() {
        when(offerRepositoryMock.findAllAvailable()).thenReturn(List.of(OFFER1A, OFFER1B, OFFER2A, OFFER2B));

        List<Offer> offers = offerService.getCurrentOffers(null, null);

        assertEquals(4, offers.size());
    }

    @Test
    void getCurrentOffersByShopId() {
        when(offerRepositoryMock.findAllAvailableByShopId(SHOP1_ID)).thenReturn(List.of(OFFER1A, OFFER1B));

        List<Offer> offers = offerService.getCurrentOffers(SHOP1_ID, null);

        assertEquals(2, offers.size());
    }

    @Test
    void getCurrentOffersByProductId() {
        when(offerRepositoryMock.findAllAvailableByProductId(PRODUCT_A_ID)).thenReturn(List.of(OFFER1A, OFFER2A));

        List<Offer> offers = offerService.getCurrentOffers(null, PRODUCT_A_ID);

        assertEquals(2, offers.size());
    }

    @Test
    void getCurrentOffersByShopIdAndProductId() {
        when(offerRepositoryMock.findAllAvailableByShopIdAndProductId(SHOP2_ID, PRODUCT_B_ID)).thenReturn(List.of(OFFER2B));

        List<Offer> offers = offerService.getCurrentOffers(SHOP2_ID, PRODUCT_B_ID);

        assertEquals(1, offers.size());
    }

    @Test
    void getOffersHistory() {
        when(offerRepositoryMock.findAllByCreatedAtBetween(any(), any()))
                .thenReturn(List.of(OFFER1A, OFFER1B, OFFER2A, OFFER2B));

        List<Offer> offers = offerService.getOffersHistory(null, null, null, null);

        assertEquals(4, offers.size());
    }

    @Test
    void getOffersHistoryByShopId() {
        when(offerRepositoryMock.findAllByCreatedAtBetweenAndPackShopId(any(), any(), eq(SHOP1_ID)))
                .thenReturn(List.of(OFFER1A, OFFER1B));

        List<Offer> offers = offerService.getOffersHistory(SHOP1_ID, null, null, null);

        assertEquals(2, offers.size());
    }

    @Test
    void getOffersHistoryByProductId() {
        when(offerRepositoryMock.findAllByCreatedAtBetweenAndPackLotProductId(any(), any(), eq(PRODUCT_A_ID)))
                .thenReturn(List.of(OFFER1A, OFFER2A));

        List<Offer> offers = offerService.getOffersHistory(null, PRODUCT_A_ID, null, null);

        assertEquals(2, offers.size());
    }

    @Test
    void getOffersHistoryByShopIdAndProductId() {
        when(offerRepositoryMock.findAllByCreatedAtBetweenAndPackShopIdAndPackLotProductId(
                any(), any(), eq(SHOP2_ID), eq(PRODUCT_B_ID))).thenReturn(List.of(OFFER2B));

        List<Offer> offers = offerService.getOffersHistory(SHOP2_ID, PRODUCT_B_ID, null, null);

        assertEquals(1, offers.size());
    }
}