package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.domain.Shop;
import com.i8ai.training.storeapi.service.*;
import com.i8ai.training.storeapi.service.dto.ExistenceDTO;
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
    public ExistenceServiceImpl(ProductService productService, ShopService shopService, LotService lotService, PackService packService, SaleService saleService) {
        this.productService = productService;
        this.shopService = shopService;
        this.lotService = lotService;
        this.packService = packService;
        this.saleService = saleService;
    }

    @Override
    public List<ExistenceDTO> getAllProductsExistenceInMain() {
        return productService.getAllProducts().stream().map(product -> getProductExistenceInMain(product.getId())).collect(Collectors.toList());
    }

    @Override
    public ExistenceDTO getProductExistenceInMain(Long productId) {
        Product product = productService.getProduct(productId);

        Double amount = lotService.getProductReceivedAmount(productId) - packService.getProductDeliveredAmount(productId);

        return new ExistenceDTO(amount, product, null);
    }

    @Override
    public List<ExistenceDTO> getProductExistenceInAllShops(Long productId) {
        return shopService.getAllShops().stream().map(shop -> getProductExistenceInShop(productId, shop.getId())).collect(Collectors.toList());
    }

    @Override
    public ExistenceDTO getProductExistenceInShop(Long productId, Long shopId) {
        Product product = productService.getProduct(productId);
        Shop shop = shopService.getShop(shopId);

        Double amount = packService.getProductDeliveredToShopAmount(productId, shopId) - saleService.getProductSoldInShopAmount(productId, shopId);

        return new ExistenceDTO(amount, product, shop);
    }
}
