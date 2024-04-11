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
        return Balance.builder()
                .spent(saleService.getNetSalesExpenses(start, end))
                .income(saleService.getNetSalesIncome(start, end))
                .build();
    }

    @Override
    public List<Balance> getBalancesPerProduct(Date start, Date end) {
        return productService.getAllProducts().stream().map(product -> Balance.builder()
                .spent(saleService.getSalesExpensesByProduct(product.getId(), start, end))
                .income(saleService.getSalesIncomeByProduct(product.getId(), start, end))
                .product(product)
                .build()
        ).toList();
    }

    @Override
    public List<Balance> getBalancesPerShop(Date start, Date end) {
        return shopService.getAllShops().stream().map(shop -> Balance.builder()
                .spent(saleService.getSalesExpensesByShop(shop.getId(), start, end))
                .income(saleService.getSalesIncomeByShop(shop.getId(), start, end))
                .shop(shop)
                .build()
        ).toList();
    }

    @Override
    public List<Balance> getBalancesPerProductPerShop(Date start, Date end) {
        List<Product> products = productService.getAllProducts();
        return shopService.getAllShops().stream().flatMap(shop -> products.stream().map(product -> Balance.builder()
                .spent(saleService.getSalesExpensesByProductAndShop(product.getId(), shop.getId(), start, end))
                .income(saleService.getSalesIncomeByProductAndShop(product.getId(), shop.getId(), start, end))
                .product(product)
                .shop(shop)
                .build()
        )).toList();
    }

    @Override
    public Balance getBalanceByProduct(Long productId, Date start, Date end) {
        return Balance.builder()
                .spent(saleService.getSalesExpensesByProduct(productId, start, end))
                .income(saleService.getSalesIncomeByProduct(productId, start, end))
                .product(productService.getProduct(productId))
                .build();
    }

    @Override
    public Balance getBalanceByShop(Long shopId, Date start, Date end) {
        return Balance.builder()
                .spent(saleService.getSalesExpensesByShop(shopId, start, end))
                .income(saleService.getSalesIncomeByShop(shopId, start, end))
                .shop(shopService.getShop(shopId))
                .build();
    }

    @Override
    public List<Balance> getBalancesByProductPerShop(Long productId, Date start, Date end) {
        Product product = productService.getProduct(productId);
        return shopService.getAllShops().stream().map(shop -> Balance.builder()
                .spent(saleService.getSalesExpensesByProductAndShop(productId, shop.getId(), start, end))
                .income(saleService.getSalesIncomeByProductAndShop(productId, shop.getId(), start, end))
                .product(product)
                .shop(shop)
                .build()
        ).toList();
    }

    @Override
    public List<Balance> getBalancesByShopPerProduct(Long shopId, Date start, Date end) {
        Shop shop = shopService.getShop(shopId);
        return productService.getAllProducts().stream().map(product -> Balance.builder()
                .spent(saleService.getSalesExpensesByProductAndShop(product.getId(), shopId, start, end))
                .income(saleService.getSalesIncomeByProductAndShop(product.getId(), shopId, start, end))
                .product(product)
                .shop(shop).build()
        ).toList();
    }

    @Override
    public Balance getBalanceByProductAndShop(Long productId, Long shopId, Date start, Date end) {
        return Balance.builder()
                .spent(saleService.getSalesExpensesByProductAndShop(productId, shopId, start, end))
                .income(saleService.getSalesIncomeByProductAndShop(productId, shopId, start, end))
                .product(productService.getProduct(productId))
                .shop(shopService.getShop(shopId))
                .build();
    }
}
