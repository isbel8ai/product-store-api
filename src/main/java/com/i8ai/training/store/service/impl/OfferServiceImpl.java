package com.i8ai.training.store.service.impl;

import com.i8ai.training.store.error.ElementNotFoundException;
import com.i8ai.training.store.model.Offer;
import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.repository.OfferRepository;
import com.i8ai.training.store.rest.dto.OfferDto;
import com.i8ai.training.store.service.OfferService;
import com.i8ai.training.store.service.PackService;
import com.i8ai.training.store.util.DateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final PackService packService;

    private final OfferRepository offerRepository;

    @Override
    @Transactional
    public Offer createOffer(OfferDto offerDto) {
        Pack pack = packService.getActivePack(offerDto.shopId(), offerDto.productId());

        Offer offer = Offer.builder()
                .pack(pack)
                .price(offerDto.price())
                .discount(offerDto.discount())
                .createdAt(DateTimeUtils.dateTimeOrNow(offerDto.createdAt())).build();

        return offerRepository.save(offer);
    }

    @Override
    public Offer getOffer(Long offerId) {
        return offerRepository.findById(offerId).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public List<Offer> getCurrentOffers(Long shopId, Long productId) {
        List<Offer> allOffers;
        if (productId == null) {
            if (shopId == null) {
                allOffers = offerRepository.findAllAvailable();
            } else {
                allOffers = offerRepository.findAllAvailableByShopId(shopId);
            }
        } else {
            if (shopId == null) {
                allOffers = offerRepository.findAllAvailableByProductId(productId);
            } else {
                allOffers = offerRepository.findAllAvailableByShopIdAndProductId(shopId, productId);
            }
        }

        return allOffers.stream()
                .collect(Collectors.groupingBy(offer -> offer.getPack().getId())).values().stream()
                .map(offers -> offers.get(0))
                .toList();
    }

    @Override
    public List<Offer> getOffersHistory(Long shopId, Long productId, LocalDateTime start, LocalDateTime end) {
        start = DateTimeUtils.dateTimeOrMin(start);
        end = DateTimeUtils.dateTimeOrMax(end);

        if (productId == null) {
            if (shopId == null) {
                return offerRepository.findAllByCreatedAtBetween(start, end);
            } else {
                return offerRepository.findAllByCreatedAtBetweenAndPackShopId(start, end, shopId);
            }
        }

        if (shopId == null) {
            return offerRepository.findAllByCreatedAtBetweenAndPackLotProductId(start, end, productId);
        } else {
            return offerRepository.findAllByCreatedAtBetweenAndPackShopIdAndPackLotProductId(
                    start, end, shopId, productId
            );
        }
    }

    @Override
    public void updateOfferPack(Long packId) {
        packService.updateSoldAmount(packId);
    }
}
