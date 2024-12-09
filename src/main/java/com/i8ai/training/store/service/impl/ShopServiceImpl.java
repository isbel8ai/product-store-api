package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.model.Shop;
import com.i8ai.training.store.repository.ShopRepository;
import com.i8ai.training.store.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    @Transactional
    public Shop createShop(Shop newShop) {
        return shopRepository.save(newShop);
    }

    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop getShop(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow();
    }

    @Override
    @Transactional
    public Shop replaceShop(Long shopId, Shop modifiedShop) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        shop.setName(modifiedShop.getName());
        shop.setAddress(modifiedShop.getAddress());
        shop.setDescription(modifiedShop.getDescription());
        return shopRepository.save(shop);
    }

    @Override
    @Transactional
    public void deleteShop(Long shopId) {
        shopRepository.deleteById(shopId);
    }
}
