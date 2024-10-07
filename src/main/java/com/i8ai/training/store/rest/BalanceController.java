package com.i8ai.training.store.rest;

import com.i8ai.training.store.service.BalanceService;
import com.i8ai.training.store.service.data.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {
    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public Balance getNetBalance(@RequestParam(required = false) Date start,
                                 @RequestParam(required = false) Date end) {
        return balanceService.getNetBalance(start, end);
    }

    @GetMapping("/product")
    public List<Balance> getBalancesPerProduct(@RequestParam(required = false) Date start,
                                               @RequestParam(required = false) Date end) {
        return balanceService.getBalancesPerProduct(start, end);
    }

    @GetMapping("/product/{productId}")
    public Balance getBalanceByProduct(@PathVariable Long productId,
                                       @RequestParam(required = false) Date start,
                                       @RequestParam(required = false) Date end) {
        return balanceService.getBalanceByProduct(productId, start, end);
    }

    @GetMapping("/product/{productId}/shop")
    public List<Balance> getBalancesByProductPerShop(@PathVariable Long productId,
                                                     @RequestParam(required = false) Date start,
                                                     @RequestParam(required = false) Date end) {
        return balanceService.getBalancesByProductPerShop(productId, start, end);
    }

    @GetMapping("/shop")
    public List<Balance> getBalancesPerShop(@RequestParam(required = false) Date start,
                                            @RequestParam(required = false) Date end) {
        return balanceService.getBalancesPerShop(start, end);
    }

    @GetMapping("/shop/{shopId}")
    public Balance getBalanceByShop(@PathVariable Long shopId,
                                    @RequestParam(required = false) Date start,
                                    @RequestParam(required = false) Date end) {
        return balanceService.getBalanceByShop(shopId, start, end);
    }

    @GetMapping("/shop/{shopId}/product")
    public List<Balance> getBalancesByShopPerProduct(@RequestParam(required = false) Date start,
                                                     @RequestParam(required = false) Date end,
                                                     @PathVariable Long shopId) {
        return balanceService.getBalancesByShopPerProduct(shopId, start, end);
    }

    @GetMapping({"/product/{productId}/shop/{shopId}", "/shop/{shopId}/product/{productId}"})
    public Balance getBalanceByProductAndShop(@PathVariable Long productId,
                                              @PathVariable Long shopId,
                                              @RequestParam(required = false) Date start,
                                              @RequestParam(required = false) Date end) {
        return balanceService.getBalanceByProductAndShop(productId, shopId, start, end);
    }

    @GetMapping({"/product/shop", "/shop/product/"})
    public List<Balance> getBalancesPerProductPerShop(@RequestParam(required = false) Date start,
                                                      @RequestParam(required = false) Date end) {
        return balanceService.getBalancesPerProductPerShop(start, end);
    }
}
