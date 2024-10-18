package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.error.ElementNotFoundException;
import com.i8ai.training.store.error.NotValidAmountException;
import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.repository.PackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.i8ai.training.store.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PackServiceImplTest {

    @Mock
    private PackRepository packRepositoryMock;

    @InjectMocks
    private PackServiceImpl packService;

    @Test
    void getPacksWithAllFilters() {
        when(packRepositoryMock.findAllByDeliveredAtBetweenAndLotProductIdAndShopId(
                        new Date(3), new Date(6), PRODUCT_A_ID, SHOP2_ID
                )
        ).thenReturn(List.of(PACK2A));

        List<Pack> pack = packService.getPacks(PRODUCT_A_ID, SHOP2_ID, new Date(3), new Date(6));

        assertEquals(1, pack.size());
    }

    @Test
    void getPacksWithShopFilter() {
        when(packRepositoryMock.findAllByDeliveredAtBetweenAndShopId(any(), any(), eq((SHOP1_ID))))
                .thenReturn(List.of(PACK1A, PACK1B));

        List<Pack> pack = packService.getPacks(null, SHOP1_ID, null, null);

        assertEquals(2, pack.size());
    }

    @Test
    void getPackWithProductFilter() {
        when(packRepositoryMock.findAllByDeliveredAtBetweenAndLotProductId(any(), any(), eq(PRODUCT_B_ID)))
                .thenReturn(List.of(PACK1B, PACK2B));

        List<Pack> pack = packService.getPacks(PRODUCT_B_ID, null, null, null);

        assertEquals(2, pack.size());
    }

    @Test
    void getAllPack() {
        when(packRepositoryMock.findAllByDeliveredAtBetween(any(), any()))
                .thenReturn(List.of(PACK1A, PACK1B, PACK2A, PACK2B));

        List<Pack> pack = packService.getPacks(null, null, null, null);

        assertEquals(4, pack.size());
    }

    @Test
    void getNotExistingPack() {
        when(packRepositoryMock.findById(PACK1A_ID)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> packService.getPack(PACK1A_ID));
    }

    @Test
    void getPack() {
        when(packRepositoryMock.findById(PACK2A_ID)).thenReturn(Optional.of(PACK2A));

        Pack pack = packService.getPack(PACK2A_ID);

        assertEquals(PACK2A_ID, pack.getId());
    }

    @Test
    void registerPackWithNotValidAmount() {
        Pack pack = Pack.builder().deliveredAt(new Date()).amount(-10.0).lot(LOT_A).shop(SHOP1).build();

        assertThrows(NotValidAmountException.class, () -> packService.registerPack(pack));
    }

    @Test
    void registerPackWithNotAvailableAmount() {
        Pack pack = Pack.builder().deliveredAt(new Date()).amount(LOT_A_AMOUNT + 10).lot(LOT_A).shop(SHOP1).build();

        assertThrows(NotValidAmountException.class, () -> packService.registerPack(pack));
    }

    @Test
    void registerPack() {
        assertDoesNotThrow(() -> packService.registerPack(PACK2B));
    }

    @Test
    void deletePack() {
        assertDoesNotThrow(() -> packService.deletePack(PACK1B_ID));
    }

    @Test
    void getProductDeliveredAmount() {
        when(packRepositoryMock.getDeliveredAmountByProductId(PRODUCT_B_ID)).thenReturn(PACK1B_AMOUNT + PACK2B_AMOUNT);

        assertEquals(PACK1B_AMOUNT + PACK2B_AMOUNT, packService.getProductDeliveredAmount(PRODUCT_B_ID));
    }

    @Test
    void getProductDeliveredToShopAmount() {
        when(packRepositoryMock.getDeliveredAmountByProductIdAndShopId(PRODUCT_A_ID, SHOP2_ID)).thenReturn(PACK2A_AMOUNT);

        assertEquals(PACK2A_AMOUNT, packService.getProductDeliveredToShopAmount(PRODUCT_A_ID, SHOP2_ID));
    }
}
