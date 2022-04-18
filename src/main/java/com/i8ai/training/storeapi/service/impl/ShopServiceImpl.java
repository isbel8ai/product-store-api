package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.model.Shop;
import com.i8ai.training.storeapi.repository.ShopRepository;
import com.i8ai.training.storeapi.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop addShop(Shop newShop) {
        return shopRepository.save(newShop);
    }

    @Override
    public Shop getShop(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow();
    }

    @Override
    public Shop replaceShop(Long shopId, Shop modifiedShop) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        shop.setName(modifiedShop.getName());
        shop.setAddress(modifiedShop.getAddress());
        shop.setDescription(modifiedShop.getDescription());
        return shopRepository.save(shop);
    }

    @Override
    public void deleteShop(Long shopId) {
        shopRepository.deleteById(shopId);
    }

}
