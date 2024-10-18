package com.i8ai.training.store.service;

import com.i8ai.training.store.model.Shop;

import java.util.List;

public interface ShopService {

    Shop createShop(Shop newShop);

    Shop getShop(Long shopId);

    List<Shop> getAllShops();

    Shop replaceShop(Long shopId, Shop modifiedShop);

    void deleteShop(Long shopId);

}
