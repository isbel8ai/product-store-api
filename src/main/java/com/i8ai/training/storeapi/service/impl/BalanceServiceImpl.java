package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Product;
import com.i8ai.training.storeapi.domain.Sale;
import com.i8ai.training.storeapi.domain.Shop;
import com.i8ai.training.storeapi.service.BalanceService;
import com.i8ai.training.storeapi.service.ProductService;
import com.i8ai.training.storeapi.service.SaleService;
import com.i8ai.training.storeapi.service.ShopService;
import com.i8ai.training.storeapi.service.dto.BalanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalanceServiceImpl implements BalanceService {
    private final ShopService shopService;
    private final ProductService productService;
    private final SaleService saleService;

    @Autowired
    public BalanceServiceImpl(ShopService shopService, ProductService productService, SaleService saleService) {
        this.shopService = shopService;
        this.productService = productService;
        this.saleService = saleService;
    }

    @Override
    public BalanceDTO getNetBalance(Date start, Date end) {
        return createBalance(saleService.getSales(start, end, null, null), null, null);
    }

    @Override
    public List<BalanceDTO> getAllProductsBalances(Date start, Date end) {
        return productService.getAllProducts().stream().map(product -> getProductNetBalance(start, end, product)).collect(Collectors.toList());
    }

    @Override
    public List<BalanceDTO> getAllShopsBalances(Date start, Date end) {
        return shopService.getAllShops().stream().map(shop -> getShopNetBalance(start, end, shop)).collect(Collectors.toList());
    }

    @Override
    public BalanceDTO getProductNetBalance(Date start, Date end, Long productId) {
        Product product = productService.getProduct(productId);
        return getProductNetBalance(start, end, product);
    }

    @Override
    public BalanceDTO getShopNetBalance(Date start, Date end, Long shopId) {
        Shop shop = shopService.getShop(shopId);
        return getShopNetBalance(start, end, shop);
    }

    @Override
    public List<BalanceDTO> getProductAllShopsBalances(Date start, Date end, Long productId) {
        return shopService.getAllShops().stream().map(shop -> getProductShopNetBalance(start, end, productId, shop.getId())).collect(Collectors.toList());
    }

    @Override
    public List<BalanceDTO> getShopAllProductsBalances(Date start, Date end, Long shopId) {
        return productService.getAllProducts().stream().map(product -> getProductShopNetBalance(start, end, product.getId(), shopId)).collect(Collectors.toList());
    }

    @Override
    public BalanceDTO getProductShopNetBalance(Date start, Date end, Long productId, Long shopId) {
        Product product = productService.getProduct(productId);
        Shop shop = shopService.getShop(shopId);

        return createBalance(saleService.getSales(start, end, productId, shopId), product, shop);
    }

    private BalanceDTO getProductNetBalance(Date start, Date end, Product product) {
        return createBalance(saleService.getSales(start, end, product.getId(), null), product, null);
    }

    private BalanceDTO getShopNetBalance(Date start, Date end, Shop shop) {
        return createBalance(saleService.getSales(start, end, null, shop.getId()), null, shop);
    }

    private BalanceDTO createBalance(List<Sale> sales, Product product, Shop shop) {
        double spent = 0.0;
        double income = 0.0;

        for (Sale sale : sales) {
            spent += sale.getAmount() * sale.getPack().getLot().getCost();
            income += sale.getAmount() * sale.getPrice();
        }

        return new BalanceDTO(spent, income, product, shop);
    }
}
