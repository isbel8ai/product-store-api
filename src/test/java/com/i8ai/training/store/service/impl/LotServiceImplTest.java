package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.exception.ElementNotFoundException;
import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.repository.LotRepository;
import com.i8ai.training.store.rest.dto.LotDto;
import com.i8ai.training.store.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.i8ai.training.store.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotServiceImplTest {

    @Mock
    private LotRepository lotRepositoryMock;

    @Mock
    private ProductService productServiceMock;

    @InjectMocks
    private LotServiceImpl lotService;

    @Test
    void getLotsWithAllFilters() {
        when(lotRepositoryMock.findAllByAcquiredAtBetweenAndProductId(notNull(), notNull(), eq(PRODUCT_A_ID)))
                .thenReturn(List.of(LOT_A));

        List<Lot> lots = lotService
                .getLots(PRODUCT_A_ID, LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(20));

        assertEquals(1, lots.size());
    }

    @Test
    void getLotsWithProductId() {
        when(lotRepositoryMock.findAllByAcquiredAtBetweenAndProductId(notNull(), notNull(), eq(PRODUCT_B_ID)))
                .thenReturn(List.of(LOT_B));

        List<Lot> lots = lotService.getLots(PRODUCT_B_ID, null, null);

        assertEquals(1, lots.size());
    }

    @Test
    void getLotsWithStartDateAndEndDate() {
        when(lotRepositoryMock.findAllByAcquiredAtBetween(notNull(), notNull())).thenReturn(List.of(LOT_A, LOT_B));

        List<Lot> lots = lotService
                .getLots(null, LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(25));

        assertEquals(2, lots.size());
    }

    @Test
    void getLotsWithEndDate() {
        when(lotRepositoryMock.findAllByAcquiredAtBetween(notNull(), notNull())).thenReturn(List.of(LOT_A, LOT_B));

        List<Lot> lots = lotService.getLots(null, null, LocalDateTime.now().plusMinutes(10));

        assertEquals(2, lots.size());
    }

    @Test
    void getLotsWithStartDate() {
        when(lotRepositoryMock.findAllByAcquiredAtBetween(notNull(), notNull())).thenReturn(List.of(LOT_B));

        List<Lot> lots = lotService.getLots(null, LocalDateTime.now().plusMinutes(5), null);

        assertEquals(1, lots.size());
    }

    @Test
    void getAllLots() {
        when(lotRepositoryMock.findAllByAcquiredAtBetween(notNull(), notNull())).thenReturn(List.of(LOT_A, LOT_B));

        List<Lot> lots = lotService.getLots(null, null, null);

        assertEquals(2, lots.size());
    }

    @Test
    void getNotExistingLot() {
        when(lotRepositoryMock.findById(LOT_B_ID)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> lotService.getLot(LOT_B_ID));
    }

    @Test
    void getLot() {
        when(lotRepositoryMock.findById(LOT_B_ID)).thenReturn(Optional.of(LOT_B));

        Lot lot = lotService.getLot(LOT_B_ID);

        assertEquals(LOT_B_ID, lot.getId());
    }

    @Test
    void registerLot() {
        when(productServiceMock.getProduct(PRODUCT_A_ID)).thenReturn(PRODUCT_A);

        lotService.registerLot(new LotDto(LOT_A));

        verify(lotRepositoryMock).save(argThat(lot -> lot.getProduct().getId().equals(PRODUCT_A_ID)));
    }

    @Test
    void deleteLot() {
        lotService.deleteLot(LOT_B_ID);

        verify(lotRepositoryMock).deleteById(LOT_B_ID);
    }

    @Test
    void getProductReceivedAmount() {
        when(lotRepositoryMock.getAmountArrivedByProductId(PRODUCT_A_ID)).thenReturn(LOT_A_AMOUNT);

        Double result = lotService.getProductReceivedAmount(PRODUCT_A_ID);

        assertEquals(LOT_A_AMOUNT, result);
    }
}
