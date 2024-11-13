package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.error.ElementNotFoundException;
import com.i8ai.training.store.error.NotValidAmountException;
import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.repository.PackRepository;
import com.i8ai.training.store.rest.dto.PackDto;
import com.i8ai.training.store.service.LotService;
import com.i8ai.training.store.service.ShopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.i8ai.training.store.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PackServiceImplTest {

    @Mock
    private PackRepository packRepositoryMock;

    @Mock
    private LotService lotServiceMock;

    @Mock
    private ShopService shopServiceMock;

    @InjectMocks
    private PackServiceImpl packService;

    @Test
    void getPacksWithAllFilters() {
        when(packRepositoryMock.findAllByReceivedAtBetweenAndLotProductIdAndShopId(
                any(), any(), eq(PRODUCT_A_ID), eq(SHOP2_ID)
        )).thenReturn(List.of(PACK2A));

        List<Pack> pack = packService.getPacks(
                PRODUCT_A_ID, SHOP2_ID, LocalDateTime.now().plusMinutes(3), LocalDateTime.now().plusMinutes(6)
        );

        assertEquals(1, pack.size());
    }

    @Test
    void getPacksWithShopFilter() {
        when(packRepositoryMock.findAllByReceivedAtBetweenAndShopId(any(), any(), eq((SHOP1_ID))))
                .thenReturn(List.of(PACK1A, PACK1B));

        List<Pack> pack = packService.getPacks(null, SHOP1_ID, null, null);

        assertEquals(2, pack.size());
    }

    @Test
    void getPackWithProductFilter() {
        when(packRepositoryMock.findAllByReceivedAtBetweenAndLotProductId(any(), any(), eq(PRODUCT_B_ID)))
                .thenReturn(List.of(PACK1B, PACK2B));

        List<Pack> pack = packService.getPacks(PRODUCT_B_ID, null, null, null);

        assertEquals(2, pack.size());
    }

    @Test
    void getAllPack() {
        when(packRepositoryMock.findAllByReceivedAtBetween(any(), any()))
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
        when(lotServiceMock.getLot(LOT_A_ID)).thenReturn(LOT_A);

        PackDto packDto = new PackDto(Pack.builder()
                .receivedAt(LocalDateTime.now())
                .receivedAmount(-10.0).lot(LOT_A)
                .shop(SHOP1)
                .build());

        assertThrows(NotValidAmountException.class, () -> packService.registerPack(packDto));
    }

    @Test
    void registerPackWithNotAvailableAmount() {
        when(lotServiceMock.getLot(LOT_A_ID)).thenReturn(LOT_A);

        PackDto packDto = new PackDto(Pack.builder()
                .receivedAt(LocalDateTime.now())
                .receivedAmount(LOT_A_AMOUNT + 10)
                .lot(LOT_A)
                .shop(SHOP1)
                .build());

        assertThrows(NotValidAmountException.class, () -> packService.registerPack(packDto));
    }

    @Test
    void registerPack() {
        when(lotServiceMock.getLot(LOT_B_ID)).thenReturn(LOT_B);
        when(shopServiceMock.getShop(SHOP2_ID)).thenReturn(SHOP2);
        PackDto packDto = new PackDto(PACK2B);

        assertDoesNotThrow(() -> packService.registerPack(packDto));
    }

    @Test
    void deletePack() {
        assertDoesNotThrow(() -> packService.deletePack(PACK1B_ID));
    }

    @Test
    void getProductDeliveredAmount() {
        when(packRepositoryMock.getDeliveredAmountByProductId(PRODUCT_B_ID))
                .thenReturn(PACK1B_AMOUNT + PACK2B_AMOUNT);

        assertEquals(PACK1B_AMOUNT + PACK2B_AMOUNT, packService.getProductDeliveredAmount(PRODUCT_B_ID));
    }

    @Test
    void getProductDeliveredToShopAmount() {
        when(packRepositoryMock.getDeliveredAmountByProductIdAndShopId(PRODUCT_A_ID, SHOP2_ID))
                .thenReturn(PACK2A_AMOUNT);

        assertEquals(PACK2A_AMOUNT, packService.getProductDeliveredToShopAmount(PRODUCT_A_ID, SHOP2_ID));
    }
}
