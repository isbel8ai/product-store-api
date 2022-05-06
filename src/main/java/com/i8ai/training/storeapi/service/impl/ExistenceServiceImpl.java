package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Shop;
import com.i8ai.training.storeapi.service.*;
import com.i8ai.training.storeapi.service.data.Existence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExistenceServiceImpl implements ExistenceService {
    private final ProductService productService;
    private final ShopService shopService;
    private final LotService lotService;
    private final PackService packService;
    private final SaleService saleService;

    @Autowired
    public ExistenceServiceImpl(
            ProductService productService,
            ShopService shopService,
            LotService lotService,
            PackService packService,
            SaleService saleService) {
        this.productService = productService;
        this.shopService = shopService;
        this.lotService = lotService;
        this.packService = packService;
        this.saleService = saleService;
    }

    @Override
    public List<Existence> getAllProductsExistenceInMain() {
        return productService.getAllProducts().stream().map(
                this::getExistenceByProductInMain).collect(Collectors.toList()
        );
    }

    @Override
    public Existence getProductExistenceInMain(Long productId) {
        Product product = productService.getProduct(productId);

        return getExistenceByProductInMain(product);
    }

    @Override
    public List<Existence> getProductExistenceInAllShops(Long productId) {
        Product product = productService.getProduct(productId);

        return shopService.getAllShops().stream().map(
                shop -> getExistenceByProductAndShop(product, shop)).collect(Collectors.toList()
        );
    }

    @Override
    public Existence getProductExistenceInShop(Long productId, Long shopId) {
        Product product = productService.getProduct(productId);
        Shop shop = shopService.getShop(shopId);

        return getExistenceByProductAndShop(product, shop);
    }

    public Existence getExistenceByProductInMain(Product product) {
        Double amount = lotService.getProductReceivedAmount(product.getId()) -
                packService.getProductDeliveredAmount(product.getId());

        return new Existence(amount, product, null);
    }

    private Existence getExistenceByProductAndShop(Product product, Shop shop) {
        Double amount = packService.getProductDeliveredToShopAmount(product.getId(), shop.getId()) -
                saleService.getSoldAmountByProductAndShop(product.getId(), shop.getId());

        return new Existence(amount, product, shop);
    }
}
