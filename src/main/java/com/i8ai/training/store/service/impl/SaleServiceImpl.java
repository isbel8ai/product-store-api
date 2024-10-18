package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.error.NotValidAmountException;
import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.repository.SaleRepository;
import com.i8ai.training.store.rest.dto.SaleDto;
import com.i8ai.training.store.service.OfferService;
import com.i8ai.training.store.service.SaleService;
import com.i8ai.training.store.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final OfferService offerService;

    private final SaleRepository saleRepository;

    @Override
    public Sale registerSale(SaleDto saleDto) {
        Offer offer = offerService.getOffer(saleDto.offerId());
        if (saleDto.amount() <= 0.0 || saleDto.amount() > offer.getPack().getCurrentAmount()) {
            throw new NotValidAmountException();
        }

        Sale sale = Sale.builder()
                .offer(offer)
                .amount(saleDto.amount())
                .registeredAt(DateTimeUtils.dateOrNow(saleDto.registeredAt()))
                .build();

        offerService.updateOfferPack(offer.getPack().getId());
        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getSales(Date start, Date end, Long productId, Long shopId) {
        start = DateTimeUtils.dateOrMin(start);
        end = DateTimeUtils.dateOrNow(end);

        if (productId == null) {
            if (shopId == null) {
                return saleRepository.findAllByRegisteredAtBetween(start, end);
            } else {
                return saleRepository.findAllByRegisteredAtBetweenAndOfferPackShopId(start, end, shopId);
            }
        }

        if (shopId == null) {
            return saleRepository.findAllByRegisteredAtBetweenAndOfferPackLotProductId(start, end, productId);
        } else {
            return saleRepository.findAllByRegisteredAtBetweenAndOfferPackLotProductIdAndOfferPackShopId(
                    start, end, productId, shopId
            );
        }
    }

    @Override
    public Double getSoldAmountByProductAndShop(Long productId, Long shopId) {
        return saleRepository.getSoldAmountByProductIdAndShopId(productId, shopId);
    }

    @Override
    public Double getNetSalesIncome(Date start, Date end) {
        return saleRepository.getNetSalesIncome(DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end));
    }

    @Override
    public Double getSalesIncomeByProduct(Long productId, Date start, Date end) {
        return saleRepository.getIncomeByProductId(
                DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end), productId
        );
    }

    @Override
    public Double getSalesIncomeByShop(Long shopId, Date start, Date end) {
        return saleRepository.getIncomeByShopId(
                DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end), shopId
        );
    }

    @Override
    public Double getSalesIncomeByProductAndShop(Long productId, Long shopId, Date start, Date end) {
        return saleRepository.getIncomeByProductIdAndShopId(
                DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end), productId, shopId
        );
    }

    @Override
    public Double getNetSalesExpenses(Date start, Date end) {
        return saleRepository.getNetSalesExpenses(DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end));
    }

    @Override
    public Double getSalesExpensesByProduct(Long productId, Date start, Date end) {
        return saleRepository.getSaleExpensesByProductId(
                DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end), productId
        );
    }

    @Override
    public Double getSalesExpensesByShop(Long shopId, Date start, Date end) {
        return saleRepository.getSaleExpensesByShopId(
                DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end), shopId
        );
    }

    @Override
    public Double getSalesExpensesByProductAndShop(Long productId, Long shopId, Date start, Date end) {
        return saleRepository.getSaleExpensesByProductIdAndShopId(
                DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrNow(end), productId, shopId
        );
    }
}
