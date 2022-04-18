package com.i8ai.training.storeapi.service;

import com.i8ai.training.storeapi.model.Sale;

import java.util.Date;
import java.util.List;

public interface SaleService {
    List<Sale> getSales(Date start, Date end, Long productId, Long shopId);

    Sale registerSale(Sale newSale);

    void deleteSale(Long saleId);

    Double getProductSoldInShopAmount(Long productId, Long shopId);
}
