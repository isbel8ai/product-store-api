package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.model.Product;
import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.service.BalanceService;
import com.i8ai.training.store.service.ProductService;
import com.i8ai.training.store.service.SaleService;
import com.i8ai.training.store.service.ShopService;
import com.i8ai.training.store.service.data.Balance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final ShopService shopService;

    private final ProductService productService;

    private final SaleService saleService;

    @Override
    public Balance getNetBalance(LocalDateTime start, LocalDateTime end) {
        return Balance.builder()
                .spent(saleService.getNetSalesExpenses(start, end))
                .income(saleService.getNetSalesIncome(start, end))
                .build();
    }

    @Override
    public List<Balance> getBalancesPerProduct(LocalDateTime start, LocalDateTime end) {
        return productService.getAllProducts().stream().map(product -> Balance.builder()
                .spent(saleService.getSalesExpensesByProduct(product.getId(), start, end))
                .income(saleService.getSalesIncomeByProduct(product.getId(), start, end))
                .product(product)
                .build()
        ).toList();
    }

    @Override
    public List<Balance> getBalancesPerShop(LocalDateTime start, LocalDateTime end) {
        return shopService.getAllShops().stream().map(shop -> Balance.builder()
                .spent(saleService.getSalesExpensesByShop(shop.getId(), start, end))
                .income(saleService.getSalesIncomeByShop(shop.getId(), start, end))
                .shop(shop)
                .build()
        ).toList();
    }

    @Override
    public List<Balance> getBalancesPerProductPerShop(LocalDateTime start, LocalDateTime end) {
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
    public Balance getBalanceByProduct(Long productId, LocalDateTime start, LocalDateTime end) {
        return Balance.builder()
                .spent(saleService.getSalesExpensesByProduct(productId, start, end))
                .income(saleService.getSalesIncomeByProduct(productId, start, end))
                .product(productService.getProduct(productId))
                .build();
    }

    @Override
    public Balance getBalanceByShop(Long shopId, LocalDateTime start, LocalDateTime end) {
        return Balance.builder()
                .spent(saleService.getSalesExpensesByShop(shopId, start, end))
                .income(saleService.getSalesIncomeByShop(shopId, start, end))
                .shop(shopService.getShop(shopId))
                .build();
    }

    @Override
    public List<Balance> getBalancesByProductPerShop(Long productId, LocalDateTime start, LocalDateTime end) {
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
    public List<Balance> getBalancesByShopPerProduct(Long shopId, LocalDateTime start, LocalDateTime end) {
        Shop shop = shopService.getShop(shopId);
        return productService.getAllProducts().stream().map(product -> Balance.builder()
                .spent(saleService.getSalesExpensesByProductAndShop(product.getId(), shopId, start, end))
                .income(saleService.getSalesIncomeByProductAndShop(product.getId(), shopId, start, end))
                .product(product)
                .shop(shop).build()
        ).toList();
    }

    @Override
    public Balance getBalanceByProductAndShop(Long productId, Long shopId, LocalDateTime start, LocalDateTime end) {
        return Balance.builder()
                .spent(saleService.getSalesExpensesByProductAndShop(productId, shopId, start, end))
                .income(saleService.getSalesIncomeByProductAndShop(productId, shopId, start, end))
                .product(productService.getProduct(productId))
                .shop(shopService.getShop(shopId))
                .build();
    }
}
