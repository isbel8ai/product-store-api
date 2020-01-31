package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.domain.Sale;
import com.i8ai.training.storeapi.repository.SaleRepository;
import com.i8ai.training.storeapi.service.PackService;
import com.i8ai.training.storeapi.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final PackService packService;

    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, PackService packService) {
        this.saleRepository = saleRepository;
        this.packService = packService;
    }

    @Override
    public List<Sale> getSales(Date start, Date end, Long productId, Long shopId) {
        if (start == null) start = new Date(0);
        if (end == null) end = new Date();

        if (productId == null) {
            if (shopId == null) {
                return saleRepository.findAllByRegisteredBetween(start, end);
            } else {
                return saleRepository.findAllByRegisteredBetweenAndPackShopId(start, end, shopId);
            }
        }

        if (shopId == null) {
            return saleRepository.findAllByRegisteredBetweenAndPackLotProductId(start, end, productId);
        } else {
            return saleRepository.findAllByRegisteredBetweenAndPackLotProductIdAndPackShopId(start, end, productId, shopId);
        }
    }

    private Double getCurrentPackAmount(Long packId) {
        Double initPackAmount = packService.getPack(packId).getAmount();
        Double alreadySoldPackAmount = saleRepository.getSoldAmountByPackId(packId);
        return initPackAmount - alreadySoldPackAmount;
    }

    @Override
    public Sale registerSale(Sale newSale) {
        if (newSale.getAmount() > getCurrentPackAmount(newSale.getPack().getId())) {
            throw new RuntimeException("Not enough amount in pack to register the sale");
        }
        return saleRepository.save(newSale);
    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }

    @Override
    public Double getProductSoldInShopAmount(Long productId, Long shopId) {
        return saleRepository.getSoldAmountByProductIdAndShopId(productId, shopId);
    }
}
