package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.model.Shop;

import java.util.List;

public interface ShopService {

    List<Shop> getAllShops();

    Shop addShop(Shop newShop);

    Shop getShop(Long shopId);

    Shop replaceShop(Long shopId, Shop modifiedShop);

    void deleteShop(Long shopId);

}
