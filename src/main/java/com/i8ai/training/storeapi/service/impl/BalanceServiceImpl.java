package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.model.Product;
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
        return new Balance(
                saleService.getNetSalesExpenses(start, end),
                saleService.getNetSalesIncome(start, end),
                null,
                null
        );
    }

    @Override
    public List<Balance> getBalancesPerProduct(Date start, Date end) {
        return productService.getAllProducts().stream()
                .map(product -> new Balance(
                        saleService.getSalesExpensesByProduct(product.getId(), start, end),
                        saleService.getSalesIncomeByProduct(product.getId(), start, end),
                        product,
                        null
                )).collect(Collectors.toList());
    }

    @Override
    public List<Balance> getBalancesPerShop(Date start, Date end) {
        return shopService.getAllShops().stream()
                .map(shop -> new Balance(
                        saleService.getSalesExpensesByShop(shop.getId(), start, end),
                        saleService.getSalesIncomeByShop(shop.getId(), start, end),
                        null,
                        shop
                )).collect(Collectors.toList());
    }

    @Override
    public List<Balance> getBalancesPerProductPerShop(Date start, Date end) {
        List<Product> products = productService.getAllProducts();
        return shopService.getAllShops().stream().flatMap(shop ->
                products.stream().map(product ->
                        new Balance(
                                saleService.getSalesExpensesByProductAndShop(product.getId(), shop.getId(), start, end),
                                saleService.getSalesIncomeByProductAndShop(product.getId(), shop.getId(), start, end),
                                product,
                                shop
                        )
                )).collect(Collectors.toList());
    }

    @Override
    public Balance getBalanceByProduct(Long productId, Date start, Date end) {
        return new Balance(
                saleService.getSalesExpensesByProduct(productId, start, end),
                saleService.getSalesIncomeByProduct(productId, start, end),
                productService.getProduct(productId),
                null
        );
    }

    @Override
    public Balance getBalanceByShop(Long shopId, Date start, Date end) {
        return new Balance(
                saleService.getSalesExpensesByShop(shopId, start, end),
                saleService.getSalesIncomeByShop(shopId, start, end),
                null,
                shopService.getShop(shopId)
        );
    }

    @Override
    public List<Balance> getBalancesByProductPerShop(Long productId, Date start, Date end) {
        Product product = productService.getProduct(productId);
        return shopService.getAllShops().stream().map(shop -> new Balance(
                saleService.getSalesExpensesByProductAndShop(productId, shop.getId(), start, end),
                saleService.getSalesIncomeByProductAndShop(productId, shop.getId(), start, end),
                product,
                shop
        )).collect(Collectors.toList());
    }

    @Override
    public List<Balance> getBalancesByShopPerProduct(Long shopId, Date start, Date end) {
        Shop shop = shopService.getShop(shopId);
        return productService.getAllProducts().stream().map(product -> new Balance(
                saleService.getSalesExpensesByProductAndShop(product.getId(), shopId, start, end),
                saleService.getSalesIncomeByProductAndShop(product.getId(), shopId, start, end),
                product,
                shop
        )).collect(Collectors.toList());
    }

    @Override
    public Balance getBalanceByProductAndShop(Long productId, Long shopId, Date start, Date end) {
        Product product = productService.getProduct(productId);
        Shop shop = shopService.getShop(shopId);

        return new Balance(
                saleService.getSalesExpensesByProductAndShop(productId, shopId, start, end),
                saleService.getSalesIncomeByProductAndShop(productId, shopId, start, end),
                productService.getProduct(productId),
                shopService.getShop(shopId)
        );
    }
}
