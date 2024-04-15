package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.model.Shop;
import com.i8ai.training.storeapi.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopServiceImplTest {

    @Mock
    private ShopRepository shopRepositoryMock;

    @InjectMocks
    private ShopServiceImpl shopService;

    @Test
    void getAllShops() {
        when(shopRepositoryMock.findAll()).thenReturn(List.of(SHOP1, SHOP2));

        List<Shop> shops = shopService.getAllShops();

        assertEquals(2, shops.size());
    }

    @Test
    void addShop() {
        assertDoesNotThrow(() -> shopService.addShop(SHOP1));
    }

    @Test
    void getShop() {
        when(shopRepositoryMock.findById(SHOP2_ID)).thenReturn(Optional.of(SHOP2));

        Shop result = shopService.getShop(SHOP2_ID);

        assertEquals(SHOP2_NAME, result.getName());
    }

    @Test
    void replaceShop() {
        when(shopRepositoryMock.findById(SHOP1_ID)).thenReturn(Optional.of(mock(Shop.class)));

        assertDoesNotThrow(() -> shopService.replaceShop(SHOP1_ID, SHOP2));
    }

    @Test
    void deleteShop() {
        assertDoesNotThrow(() -> shopService.deleteShop(SHOP2_ID));
    }
}
