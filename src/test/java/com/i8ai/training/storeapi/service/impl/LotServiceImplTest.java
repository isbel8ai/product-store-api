package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.model.Lot;
import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.repository.LotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotServiceImplTest {

    private static final Product PRODUCT_A = new Product(PRODUCT_A_ID, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null);
    private static final Product PRODUCT_B = new Product(PRODUCT_B_ID, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null);

    private static final Lot LOT_A = new Lot(LOT_A_ID, new Date(5), LOT_A_AMOUNT, PRODUCT_A_COST, PRODUCT_A);
    private static final Lot LOT_B = new Lot(LOT_B_ID, new Date(10), LOT_B_AMOUNT, PRODUCT_B_COST, PRODUCT_B);

    @Mock
    private LotRepository lotRepositoryMock;

    @InjectMocks
    private LotServiceImpl lotService;

    @Test
    void getLotsWithAllFilters() {
        when(lotRepositoryMock.findAllByProductIdAndReceivedBetween(eq(PRODUCT_A_ID), notNull(), notNull())).thenReturn(List.of(LOT_A));

        List<Lot> lots = lotService.getLots(PRODUCT_A_ID, new Date(10), new Date(20));

        assertEquals(1, lots.size());
    }

    @Test
    void getLotsWithProductId() {
        when(lotRepositoryMock.findAllByProductIdAndReceivedBetween(eq(PRODUCT_B_ID), notNull(), notNull())).thenReturn(List.of(LOT_B));

        List<Lot> lots = lotService.getLots(PRODUCT_B_ID, null, null);

        assertEquals(1, lots.size());
    }

    @Test
    void getLotsWithStartDateAndEndDate() {
        when(lotRepositoryMock.findAllByReceivedBetween(notNull(), notNull())).thenReturn(List.of(LOT_A, LOT_B));

        List<Lot> lots = lotService.getLots(null, new Date(10), new Date(25));

        assertEquals(2, lots.size());
    }

    @Test
    void getLotsWithEndDate() {
        when(lotRepositoryMock.findAllByReceivedBetween(notNull(), notNull())).thenReturn(List.of(LOT_A, LOT_B));

        List<Lot> lots = lotService.getLots(null, null, new Date(10));

        assertEquals(2, lots.size());
    }

    @Test
    void getLotsWithStartDate() {
        when(lotRepositoryMock.findAllByReceivedBetween(notNull(), notNull())).thenReturn(List.of(LOT_B));

        List<Lot> lots = lotService.getLots(null, new Date(5), null);

        assertEquals(1, lots.size());
    }

    @Test
    void getAllLots() {
        when(lotRepositoryMock.findAllByReceivedBetween(notNull(), notNull())).thenReturn(List.of(LOT_A, LOT_B));

        List<Lot> lots = lotService.getLots(null, null, null);

        assertEquals(2, lots.size());
    }

    @Test
    void registerLot() {
        lotService.registerLot(LOT_A);

        verify(lotRepositoryMock).save(LOT_A);
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
