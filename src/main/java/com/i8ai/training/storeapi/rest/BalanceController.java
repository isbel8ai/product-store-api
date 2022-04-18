package com.i8ai.training.storeapi.rest;

import com.i8ai.training.storeapi.service.BalanceService;
import com.i8ai.training.storeapi.rest.dto.BalanceDTO;
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
    public BalanceDTO getNetBalance(@RequestParam(required = false) Date start,
                                    @RequestParam(required = false) Date end) {
        return balanceService.getNetBalance(start, end);
    }

    @GetMapping("/product")
    public List<BalanceDTO> getAllProductsBalances(@RequestParam(required = false) Date start,
                                                   @RequestParam(required = false) Date end) {
        return balanceService.getAllProductsBalances(start, end);
    }

    @GetMapping("/product/{productId}")
    public BalanceDTO getProductNetBalance(@RequestParam(required = false) Date start,
                                           @RequestParam(required = false) Date end,
                                           @PathVariable Long productId) {
        return balanceService.getProductNetBalance(start, end, productId);
    }

    @GetMapping("/product/{productId}/shop")
    public List<BalanceDTO> getProductAllShopsBalances(@RequestParam(required = false) Date start,
                                                       @RequestParam(required = false) Date end,
                                                       @PathVariable Long productId) {
        return balanceService.getProductAllShopsBalances(start, end, productId);
    }

    @GetMapping("/shop")
    public List<BalanceDTO> getAllShopsBalances(@RequestParam(required = false) Date start,
                                                @RequestParam(required = false) Date end) {
        return balanceService.getAllShopsBalances(start, end);
    }

    @GetMapping("/shop/{shopId}")
    public BalanceDTO getShopNetBalance(@RequestParam(required = false) Date start,
                                        @RequestParam(required = false) Date end,
                                        @PathVariable Long shopId) {
        return balanceService.getShopNetBalance(start, end, shopId);
    }

    @GetMapping("/shop/{shopId}/product")
    public List<BalanceDTO> getShopAllProductsBalances(@RequestParam(required = false) Date start,
                                                       @RequestParam(required = false) Date end,
                                                       @PathVariable Long shopId) {
        return balanceService.getShopAllProductsBalances(start, end, shopId);
    }

    @GetMapping({"/product/{productId}/shop/{shopId}", "/shop/{shopId}/product/{productId}"})
    public BalanceDTO getProductShopNetBalance(@RequestParam(required = false) Date start,
                                               @RequestParam(required = false) Date end,
                                               @PathVariable Long productId,
                                               @PathVariable Long shopId) {
        return balanceService.getProductShopNetBalance(start, end, productId, shopId);
    }
}
