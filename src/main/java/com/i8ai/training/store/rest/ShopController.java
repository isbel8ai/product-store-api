package com.i8ai.training.store.rest;


import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Shop addShop(@Valid @RequestBody Shop newShop) {
        return shopService.addShop(newShop);
    }

    @GetMapping("/{shopId}")
    public Shop getShop(@PathVariable Long shopId) {
        return shopService.getShop(shopId);
    }

    @PutMapping(value = "/{shopId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Shop replaceShop(@PathVariable Long shopId, @Valid @RequestBody Shop modifiedShop) {
        return shopService.replaceShop(shopId, modifiedShop);
    }

    @DeleteMapping("/{shopId}")
    public void deleteShop(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
    }
}
