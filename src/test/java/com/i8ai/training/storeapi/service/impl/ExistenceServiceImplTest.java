package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.service.*;
import com.i8ai.training.storeapi.service.data.Existence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.i8ai.training.storeapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExistenceServiceImplTest {

    @Mock
    ProductService productServiceMock;

    @Mock
    ShopService shopServiceMock;

    @Mock
    LotService lotServiceMock;

    @Mock
    PackService packServiceMock;

    @Mock
    SaleService saleServiceMock;

    @InjectMocks
    private ExistenceServiceImpl existenceService;

    @Test
    void getAllProductsExistenceInMain() {
        when(productServiceMock.getAllProducts()).thenReturn(List.of(PRODUCT_A, PRODUCT_B));
        when(lotServiceMock.getProductReceivedAmount(PRODUCT_A_ID)).thenReturn(300.0);
        when(lotServiceMock.getProductReceivedAmount(PRODUCT_B_ID)).thenReturn(500.0);
        when(packServiceMock.getProductDeliveredAmount(PRODUCT_A_ID)).thenReturn(150.0);
        when(packServiceMock.getProductDeliveredAmount(PRODUCT_B_ID)).thenReturn(200.0);

        List<Existence> existences = existenceService.getAllProductsExistenceInMain();

        assertEquals(2, existences.size());
        assertNull(existences.get(0).getShop());
        assertEquals(PRODUCT_A_NAME, existences.get(0).getProduct().getName());
        assertEquals(150.0, existences.get(0).getAmount());
        assertNull(existences.get(1).getShop());
        assertEquals(PRODUCT_B_NAME, existences.get(1).getProduct().getName());
        assertEquals(300.0, existences.get(1).getAmount());
    }

    @Test
    void getProductExistenceInMain() {
        when(productServiceMock.getProduct(PRODUCT_B_ID)).thenReturn(PRODUCT_B);
        when(lotServiceMock.getProductReceivedAmount(PRODUCT_B_ID)).thenReturn(500.0);
        when(packServiceMock.getProductDeliveredAmount(PRODUCT_B_ID)).thenReturn(200.0);

        assertEquals(300.0, existenceService.getProductExistenceInMain(PRODUCT_B_ID).getAmount());
    }

    @Test
    void getProductExistenceInAllShops() {
        when(productServiceMock.getProduct(PRODUCT_B_ID)).thenReturn(PRODUCT_B);
        when(shopServiceMock.getAllShops()).thenReturn(List.of(SHOP1, SHOP2));
        when(packServiceMock.getProductDeliveredToShopAmount(PRODUCT_B_ID, SHOP1_ID)).thenReturn(600.0);
        when(packServiceMock.getProductDeliveredToShopAmount(PRODUCT_B_ID, SHOP2_ID)).thenReturn(550.0);
        when(saleServiceMock.getSoldAmountByProductAndShop(PRODUCT_B_ID, SHOP1_ID)).thenReturn(250.0);
        when(saleServiceMock.getSoldAmountByProductAndShop(PRODUCT_B_ID, SHOP2_ID)).thenReturn(350.0);

        List<Existence> existences = existenceService.getProductExistenceInAllShops(PRODUCT_B_ID);

        assertEquals(2, existences.size());
        assertEquals(SHOP1_NAME, existences.get(0).getShop().getName());
        assertEquals(PRODUCT_B_NAME, existences.get(0).getProduct().getName());
        assertEquals(350.0, existences.get(0).getAmount());
        assertEquals(SHOP2_NAME, existences.get(1).getShop().getName());
        assertEquals(PRODUCT_B_NAME, existences.get(1).getProduct().getName());
        assertEquals(200.0, existences.get(1).getAmount());
    }

    @Test
    void getProductExistenceInShop() {
        when(productServiceMock.getProduct(PRODUCT_A_ID)).thenReturn(PRODUCT_A);
        when(shopServiceMock.getShop(SHOP1_ID)).thenReturn(SHOP1);
        when(packServiceMock.getProductDeliveredToShopAmount(PRODUCT_A_ID, SHOP1_ID)).thenReturn(400.0);
        when(saleServiceMock.getSoldAmountByProductAndShop(PRODUCT_A_ID, SHOP1_ID)).thenReturn(210.0);

        assertEquals(190.0, existenceService.getProductExistenceInShop(PRODUCT_A_ID, SHOP1_ID).getAmount());
    }

}
