package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.error.ElementNotFoundException;
import com.i8ai.training.storeapi.error.NotValidAmountException;
import com.i8ai.training.storeapi.model.Lot;
import com.i8ai.training.storeapi.model.Pack;
import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Shop;
import com.i8ai.training.storeapi.repository.PackRepository;
import com.i8ai.training.storeapi.service.LotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PackServiceImplTest {

    private static final Product PRODUCT_A = new Product(PRODUCT_A_ID, PRODUCT_A_CODE, PRODUCT_A_NAME, PRODUCT_A_MEASURE, null);
    private static final Product PRODUCT_B = new Product(PRODUCT_B_ID, PRODUCT_B_CODE, PRODUCT_B_NAME, PRODUCT_B_MEASURE, null);

    private static final Shop SHOP1 = new Shop(SHOP1_ID, SHOP1_NAME, SHOP1_ADDRESS, null);
    private static final Shop SHOP2 = new Shop(SHOP2_ID, SHOP2_NAME, SHOP2_ADDRESS, null);

    private static final Lot LOT_A = new Lot(LOT_A_ID, new Date(10), LOT_A_AMOUNT, PRODUCT_A_COST, PRODUCT_A);
    private static final Lot LOT_B = new Lot(LOT_B_ID, new Date(10), LOT_B_AMOUNT, PRODUCT_B_COST, PRODUCT_B);

    private static final Pack PACK1A = new Pack(PACK1A_ID, new Date(15), PACK1A_AMOUNT, LOT_A, SHOP1);
    private static final Pack PACK1B = new Pack(PACK1B_ID, new Date(20), PACK1B_AMOUNT, LOT_B, SHOP1);
    private static final Pack PACK2A = new Pack(PACK2A_ID, new Date(25), PACK2A_AMOUNT, LOT_A, SHOP2);
    private static final Pack PACK2B = new Pack(PACK2B_ID, new Date(30), PACK2B_AMOUNT, LOT_B, SHOP2);

    @Mock
    private PackRepository packRepositoryMock;
    @Mock
    private LotService lotServiceMock;

    @InjectMocks
    private PackServiceImpl packService;

    @Test
    void getPacksWithAllFilters() {
        when(packRepositoryMock.findAllByDeliveredBetweenAndLotProductIdAndShopId(
                        new Date(3), new Date(6), PRODUCT_A_ID, SHOP2_ID
                )
        ).thenReturn(List.of(PACK2A));

        List<Pack> pack = packService.getPacks(PRODUCT_A_ID, SHOP2_ID, new Date(3), new Date(6));

        assertEquals(1, pack.size());
    }

    @Test
    void getPacksWithShopFilter() {
        when(packRepositoryMock.findAllByDeliveredBetweenAndShopId(any(), any(), eq((SHOP1_ID))))
                .thenReturn(List.of(PACK1A, PACK1B));

        List<Pack> pack = packService.getPacks(null, SHOP1_ID, null, null);

        assertEquals(2, pack.size());
    }

    @Test
    void getPackWithProductFilter() {
        when(packRepositoryMock.findAllByDeliveredBetweenAndLotProductId(any(), any(), eq(PRODUCT_B_ID)))
                .thenReturn(List.of(PACK1B, PACK2B));

        List<Pack> pack = packService.getPacks(PRODUCT_B_ID, null, null, null);

        assertEquals(2, pack.size());
    }

    @Test
    void getAllPack() {
        when(packRepositoryMock.findAllByDeliveredBetween(any(), any()))
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
        Pack pack = new Pack(null, new Date(), -10.0, LOT_A, SHOP1);

        assertThrows(NotValidAmountException.class, () -> packService.registerPack(pack));
    }

    @Test
    void registerPackWithNotAvailableAmount() {
        when(lotServiceMock.getLot(LOT_A_ID)).thenReturn(LOT_A);

        Pack pack = new Pack(null, new Date(), LOT_A_AMOUNT + 10, LOT_A, SHOP1);

        assertThrows(NotValidAmountException.class, () -> packService.registerPack(pack));
    }

    @Test
    void registerPack() {
        when(lotServiceMock.getLot(LOT_B_ID)).thenReturn(LOT_B);

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
