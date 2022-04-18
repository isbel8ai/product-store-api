package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.model.Product;
import com.i8ai.training.storeapi.model.Sale;
import com.i8ai.training.storeapi.model.Shop;
import com.i8ai.training.storeapi.service.BalanceService;
import com.i8ai.training.storeapi.service.ProductService;
import com.i8ai.training.storeapi.service.SaleService;
import com.i8ai.training.storeapi.service.ShopService;
import com.i8ai.training.storeapi.service.data.Balance;
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
    public Balance getNetBalance(Date start, Date end) {
        return createBalance(saleService.getSales(start, end, null, null), null, null);
    }

    @Override
    public List<Balance> getBalancesPerProduct(Date start, Date end) {
        return productService.getAllProducts().stream()
                .map(product -> getProductNetBalance(start, end, product))
                .collect(Collectors.toList());
    }

    @Override
    public List<Balance> getBalancesPerShop(Date start, Date end) {
        return shopService.getAllShops().stream()
                .map(shop -> getShopNetBalance(start, end, shop))
                .collect(Collectors.toList());
    }

    @Override
    public List<Balance> getBalancesPerProductPerShop(Date start, Date end) {
        List<Product> products = productService.getAllProducts();
        return shopService.getAllShops().stream()
                .flatMap(shop ->
                        products.stream().map(product ->
                                createBalance(
                                        saleService.getSales(start, end, product.getId(), shop.getId()),
                                        product,
                                        shop
                                )
                        )
                ).collect(Collectors.toList());
    }

    @Override
    public Balance getBalanceByProduct(Date start, Date end, Long productId) {
        Product product = productService.getProduct(productId);
        return getProductNetBalance(start, end, product);
    }

    @Override
    public Balance getBalanceByShop(Date start, Date end, Long shopId) {
        Shop shop = shopService.getShop(shopId);
        return getShopNetBalance(start, end, shop);
    }

    @Override
    public List<Balance> getBalancesByProductPerShop(Date start, Date end, Long productId) {
        return shopService.getAllShops().stream()
                .map(shop -> getBalanceByProductAndShop(start, end, productId, shop.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Balance> getBalancesByShopPerProduct(Date start, Date end, Long shopId) {
        return productService.getAllProducts().stream()
                .map(product -> getBalanceByProductAndShop(start, end, product.getId(), shopId))
                .collect(Collectors.toList());
    }

    @Override
    public Balance getBalanceByProductAndShop(Date start, Date end, Long productId, Long shopId) {
        Product product = productService.getProduct(productId);
        Shop shop = shopService.getShop(shopId);

        return createBalance(saleService.getSales(start, end, productId, shopId), product, shop);
    }

    private Balance getProductNetBalance(Date start, Date end, Product product) {
        return createBalance(saleService.getSales(start, end, product.getId(), null), product, null);
    }

    private Balance getShopNetBalance(Date start, Date end, Shop shop) {
        return createBalance(saleService.getSales(start, end, null, shop.getId()), null, shop);
    }

    private Balance createBalance(List<Sale> sales, Product product, Shop shop) {
        double spent = sales.stream().map(s -> s.getAmount() * s.getPack().getLot().getCost()).reduce(0.0, Double::sum);
        double income = sales.stream().map(s -> s.getAmount() * s.getPrice()).reduce(0.0, Double::sum);

        return new Balance(spent, income, product, shop);
    }
}
