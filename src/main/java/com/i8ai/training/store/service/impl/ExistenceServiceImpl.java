package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.rest.dto.ExistenceDto;
import com.i8ai.training.store.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExistenceServiceImpl implements ExistenceService {

    private final ProductService productService;

    private final ShopService shopService;

    private final LotService lotService;

    private final PackService packService;

    private final SaleService saleService;

    @Override
    public List<ExistenceDto> getAllProductsExistenceInMain() {
        return productService.getAllProducts().stream().map(this::getExistenceByProductInMain).toList();
    }

    @Override
    public ExistenceDto getProductExistenceInMain(Long productId) {
        Product product = productService.getProduct(productId);

        return getExistenceByProductInMain(product);
    }

    @Override
    public List<ExistenceDto> getProductExistenceInAllShops(Long productId) {
        Product product = productService.getProduct(productId);

        return shopService.getAllShops().stream().map(shop -> getExistenceByProductAndShop(product, shop)).toList();
    }

    @Override
    public ExistenceDto getProductExistenceInShop(Long productId, Long shopId) {
        Product product = productService.getProduct(productId);
        Shop shop = shopService.getShop(shopId);

        return getExistenceByProductAndShop(product, shop);
    }

    public ExistenceDto getExistenceByProductInMain(Product product) {
        Double amount = lotService.getProductReceivedAmount(product.getId()) -
                packService.getProductDeliveredAmount(product.getId());

        return ExistenceDto.builder().amount(amount).product(product).build();
    }

    private ExistenceDto getExistenceByProductAndShop(Product product, Shop shop) {
        Double amount = packService.getProductDeliveredToShopAmount(product.getId(), shop.getId()) -
                saleService.getSoldAmountByProductAndShop(product.getId(), shop.getId());

        return ExistenceDto.builder().amount(amount).product(product).shop(shop).build();
    }
}
