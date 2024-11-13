package com.i8ai.training.store.rest;

import com.i8ai.training.store.service.BalanceService;
import com.i8ai.training.store.service.data.Balance;
import com.i8ai.training.store.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("balance")
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping
    public Balance getNetBalance(@RequestParam(required = false) ZonedDateTime start,
                                 @RequestParam(required = false) ZonedDateTime end) {
        return balanceService.getNetBalance(DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end));
    }

    @GetMapping("product")
    public List<Balance> getBalancesPerProduct(@RequestParam(required = false) ZonedDateTime start,
                                               @RequestParam(required = false) ZonedDateTime end) {
        return balanceService.getBalancesPerProduct(
                DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end)
        );
    }

    @GetMapping("product/{productId}")
    public Balance getBalanceByProduct(@PathVariable Long productId,
                                       @RequestParam(required = false) ZonedDateTime start,
                                       @RequestParam(required = false) ZonedDateTime end) {
        return balanceService.getBalanceByProduct(
                productId, DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end)
        );
    }

    @GetMapping("product/{productId}/shop")
    public List<Balance> getBalancesByProductPerShop(@PathVariable Long productId,
                                                     @RequestParam(required = false) ZonedDateTime start,
                                                     @RequestParam(required = false) ZonedDateTime end) {
        return balanceService.getBalancesByProductPerShop(
                productId, DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end)
        );
    }

    @GetMapping("shop")
    public List<Balance> getBalancesPerShop(@RequestParam(required = false) ZonedDateTime start,
                                            @RequestParam(required = false) ZonedDateTime end) {
        return balanceService.getBalancesPerShop(DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end));
    }

    @GetMapping("shop/{shopId}")
    public Balance getBalanceByShop(@PathVariable Long shopId,
                                    @RequestParam(required = false) ZonedDateTime start,
                                    @RequestParam(required = false) ZonedDateTime end) {
        return balanceService.getBalanceByShop(shopId, DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end));
    }

    @GetMapping("shop/{shopId}/product")
    public List<Balance> getBalancesByShopPerProduct(@RequestParam(required = false) ZonedDateTime start,
                                                     @RequestParam(required = false) ZonedDateTime end,
                                                     @PathVariable Long shopId) {
        return balanceService.getBalancesByShopPerProduct(shopId, DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end));
    }

    @GetMapping({"product/{productId}/shop/{shopId}", "shop/{shopId}/product/{productId}"})
    public Balance getBalanceByProductAndShop(@PathVariable Long productId,
                                              @PathVariable Long shopId,
                                              @RequestParam(required = false) ZonedDateTime start,
                                              @RequestParam(required = false) ZonedDateTime end) {
        return balanceService.getBalanceByProductAndShop(productId, shopId, DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end));
    }

    @GetMapping({"product/shop", "shop/product/"})
    public List<Balance> getBalancesPerProductPerShop(@RequestParam(required = false) ZonedDateTime start,
                                                      @RequestParam(required = false) ZonedDateTime end) {
        return balanceService.getBalancesPerProductPerShop(DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end));
    }
}
