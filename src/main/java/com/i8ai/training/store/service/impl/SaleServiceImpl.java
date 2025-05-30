package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.exception.ElementNotFoundException;
import com.i8ai.training.store.exception.InvalidInvoiceStatusTransitionException;
import com.i8ai.training.store.exception.InvalidSaleAmountException;
import com.i8ai.training.store.exception.InvalidSaleShopForInvoiceException;
import com.i8ai.training.store.model.Invoice;
import com.i8ai.training.store.model.InvoiceStatus;
import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.repository.SaleRepository;
import com.i8ai.training.store.rest.dto.SaleDto;
import com.i8ai.training.store.service.InvoiceService;
import com.i8ai.training.store.service.OfferService;
import com.i8ai.training.store.service.PackService;
import com.i8ai.training.store.service.SaleService;
import com.i8ai.training.store.util.DateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final InvoiceService invoiceService;

    private final OfferService offerService;

    private final PackService packService;

    private final SaleRepository saleRepository;

    @Override
    @Transactional
    public Sale registerSale(SaleDto saleDto) {
        Invoice invoice = invoiceService.getInvoice(saleDto.invoiceId());
        if (invoice.getStatus().notValidTransitionTo(InvoiceStatus.OPEN)) {
            throw new InvalidInvoiceStatusTransitionException(invoice.getStatus(), InvoiceStatus.OPEN);
        }
        Long invoiceShopId = invoice.getSales().stream().findFirst()
                .map(sale -> sale.getOffer().getPack().getShop().getId())
                .orElse(null);

        Offer offer = offerService.getOffer(saleDto.offerId());
        Long saleShopId = offer.getPack().getShop().getId();
        if (invoiceShopId != null && !invoiceShopId.equals(saleShopId)) {
            throw new InvalidSaleShopForInvoiceException(saleShopId, invoiceShopId);
        }
        if (saleDto.amount() <= 0.0 || saleDto.amount() > offer.getPack().getCurrentAmount()) {
            throw new InvalidSaleAmountException(saleDto.amount(), offer.getPack());
        }

        Sale sale = Sale.builder()
                .offer(offer)
                .invoice(invoice)
                .amount(saleDto.amount())
                .registeredAt(DateTimeUtils.dateTimeOrNow(saleDto.registeredAt()))
                .build();

        saleRepository.save(sale);
        packService.updateSoldAmount(offer.getPack().getId());
        invoiceService.updateInvoice(invoice.getId(), InvoiceStatus.OPEN);
        return sale;
    }

    @Override
    public List<Sale> getSales(LocalDateTime start, LocalDateTime end, Long productId, Long shopId) {
        start = DateTimeUtils.dateTimeOrMin(start);
        end = DateTimeUtils.dateTimeOrMax(end);

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
    public Double getNetSalesIncome(LocalDateTime start, LocalDateTime end) {
        return saleRepository.getNetSalesIncome(DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end));
    }

    @Override
    public Double getSalesIncomeByProduct(Long productId, LocalDateTime start, LocalDateTime end) {
        return saleRepository.getIncomeByProductId(
                DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end), productId
        );
    }

    @Override
    public Double getSalesIncomeByShop(Long shopId, LocalDateTime start, LocalDateTime end) {
        return saleRepository.getIncomeByShopId(
                DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end), shopId
        );
    }

    @Override
    public Double getSalesIncomeByProductAndShop(Long productId, Long shopId, LocalDateTime start, LocalDateTime end) {
        return saleRepository.getIncomeByProductIdAndShopId(
                DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end), productId, shopId
        );
    }

    @Override
    public Double getNetSalesExpenses(LocalDateTime start, LocalDateTime end) {
        return saleRepository.getNetSalesExpenses(DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end));
    }

    @Override
    public Double getSalesExpensesByProduct(Long productId, LocalDateTime start, LocalDateTime end) {
        return saleRepository.getSaleExpensesByProductId(
                DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end), productId
        );
    }

    @Override
    public Double getSalesExpensesByShop(Long shopId, LocalDateTime start, LocalDateTime end) {
        return saleRepository.getSaleExpensesByShopId(
                DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end), shopId
        );
    }

    @Override
    public Double getSalesExpensesByProductAndShop(Long productId, Long shopId, LocalDateTime start, LocalDateTime end) {
        return saleRepository.getSaleExpensesByProductIdAndShopId(
                DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end), productId, shopId
        );
    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepository.delete(saleRepository.findById(saleId).orElseThrow(ElementNotFoundException::new));
    }
}
