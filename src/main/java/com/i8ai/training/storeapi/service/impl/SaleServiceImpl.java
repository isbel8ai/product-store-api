package com.i8ai.training.storeapi.service.impl;

import com.i8ai.training.storeapi.error.NotValidAmountException;
import com.i8ai.training.storeapi.model.Sale;
import com.i8ai.training.storeapi.repository.SaleRepository;
import com.i8ai.training.storeapi.service.SaleService;
import com.i8ai.training.storeapi.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> getSales(Date start, Date end, Long productId, Long shopId) {
        start = DateTimeUtils.dateOrMin(start);
        end = DateTimeUtils.dateOrMax(end);

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

    @Override
    public Sale registerSale(Sale newSale) {
        Double soldAmount = saleRepository.getSoldAmountByPackId(newSale.getPack().getId());
        if (newSale.getAmount() <= 0.0 || newSale.getAmount() > newSale.getPack().getAmount() - soldAmount) {
            throw new NotValidAmountException();
        }
        return saleRepository.save(newSale);
    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }

    @Override
    public Double getSoldAmountByProductAndShop(Long productId, Long shopId) {
        return saleRepository.getSoldAmountByProductIdAndShopId(productId, shopId);
    }

    @Override
    public Double getNetSalesIncome(Date start, Date end) {
        return saleRepository.getNetSalesIncome(DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end));
    }

    @Override
    public Double getSalesIncomeByProduct(Long productId, Date start, Date end) {
        return saleRepository.getIncomeByProductId(
                productId, DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end)
        );
    }

    @Override
    public Double getSalesIncomeByShop(Long shopId, Date start, Date end) {
        return saleRepository.getIncomeByShopId(
                shopId, DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end)
        );
    }

    @Override
    public Double getSalesIncomeByProductAndShop(Long productId, Long shopId, Date start, Date end) {
        return saleRepository.getIncomeByProductIdAndShopId(
                productId, shopId, DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end)
        );
    }

    @Override
    public Double getNetSalesExpenses(Date start, Date end) {
        return saleRepository.getNetSalesExpenses(DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end));
    }

    @Override
    public Double getSalesExpensesByProduct(Long productId, Date start, Date end) {
        return saleRepository.getSaleExpensesByProductId(
                productId, DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end)
        );
    }

    @Override
    public Double getSalesExpensesByShop(Long shopId, Date start, Date end) {
        return saleRepository.getSaleExpensesByShopId(
                shopId, DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end)
        );
    }

    @Override
    public Double getSalesExpensesByProductAndShop(Long productId, Long shopId, Date start, Date end) {
        return saleRepository.getSaleExpensesByProductIdAndShopId(
                productId, shopId, DateTimeUtils.dateOrMin(start), DateTimeUtils.dateOrMax(end)
        );
    }
}
