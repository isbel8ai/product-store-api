package com.i8ai.training.storeapi.rest;

import com.i8ai.training.storeapi.rest.dto.BalanceResponse;
import com.i8ai.training.storeapi.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/balance")
public class BalanceController {
    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public BalanceResponse getNetBalance(@RequestParam(required = false) Date start,
                                         @RequestParam(required = false) Date end) {
        return BalanceResponse.fromFilteredBalance(balanceService.getNetBalance(start, end));
    }

    @GetMapping("/product")
    public List<BalanceResponse> getBalancesPerProduct(@RequestParam(required = false) Date start,
                                                       @RequestParam(required = false) Date end) {
        return balanceService.getBalancesPerProduct(start, end).stream()
                .map(BalanceResponse::fromFilteredBalance)
                .collect(Collectors.toList());
    }

    @GetMapping("/product/{productId}")
    public BalanceResponse getBalanceByProduct(@RequestParam(required = false) Date start,
                                               @RequestParam(required = false) Date end,
                                               @PathVariable Long productId) {
        return BalanceResponse.fromFilteredBalance(balanceService.getBalanceByProduct(start, end, productId));
    }

    @GetMapping("/product/{productId}/shop")
    public List<BalanceResponse> getBalancesByProductPerShop(@RequestParam(required = false) Date start,
                                                             @RequestParam(required = false) Date end,
                                                             @PathVariable Long productId) {
        return balanceService.getBalancesByProductPerShop(start, end, productId).stream()
                .map(BalanceResponse::fromFilteredBalance)
                .collect(Collectors.toList());
    }

    @GetMapping("/shop")
    public List<BalanceResponse> getBalancesPerShop(@RequestParam(required = false) Date start,
                                                    @RequestParam(required = false) Date end) {
        return balanceService.getBalancesPerShop(start, end).stream()
                .map(BalanceResponse::fromFilteredBalance)
                .collect(Collectors.toList());
    }

    @GetMapping("/shop/{shopId}")
    public BalanceResponse getBalanceByShop(@RequestParam(required = false) Date start,
                                            @RequestParam(required = false) Date end,
                                            @PathVariable Long shopId) {
        return BalanceResponse.fromFilteredBalance(balanceService.getBalanceByShop(start, end, shopId));
    }

    @GetMapping("/shop/{shopId}/product")
    public List<BalanceResponse> getBalancesByShopPerProduct(@RequestParam(required = false) Date start,
                                                             @RequestParam(required = false) Date end,
                                                             @PathVariable Long shopId) {
        return balanceService.getBalancesByShopPerProduct(start, end, shopId).stream()
                .map(BalanceResponse::fromFilteredBalance)
                .collect(Collectors.toList());
    }

    @GetMapping({"/product/{productId}/shop/{shopId}", "/shop/{shopId}/product/{productId}"})
    public BalanceResponse getBalanceByProductAndShop(@RequestParam(required = false) Date start,
                                                      @RequestParam(required = false) Date end,
                                                      @PathVariable Long productId,
                                                      @PathVariable Long shopId) {
        return BalanceResponse.fromFilteredBalance(
                balanceService.getBalanceByProductAndShop(start, end, productId, shopId)
        );
    }

    @GetMapping({"/product/shop", "/shop/product/"})
    public List<BalanceResponse> getBalancesPerProductPerShop(@RequestParam(required = false) Date start,
                                                              @RequestParam(required = false) Date end) {
        return balanceService.getBalancesPerProductPerShop(start, end).stream()
                .map(BalanceResponse::fromFilteredBalance)
                .collect(Collectors.toList());
    }
}
