package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select o from Offer o where (o.pack.soldAmount is null or o.pack.receivedAmount - o.pack.soldAmount > 0) " +
            "order by o.createdAt desc")
    List<Offer> findAllAvailable();

    @Query("select o from Offer o where (o.pack.soldAmount is null or o.pack.receivedAmount - o.pack.soldAmount > 0) " +
            "and o.pack.shop.id = :shopId order by o.createdAt desc")
    List<Offer> findAllAvailableByShopId(Long shopId);

    @Query("select o from Offer o where (o.pack.soldAmount is null or o.pack.receivedAmount - o.pack.soldAmount > 0) " +
            "and o.pack.lot.product.id = :productId order by o.createdAt desc")
    List<Offer> findAllAvailableByProductId(Long productId);

    @Query("select o from Offer o where (o.pack.soldAmount is null or o.pack.receivedAmount - o.pack.soldAmount > 0) " +
            "and o.pack.shop.id = :shopId and o.pack.lot.product.id = :productId order by o.createdAt desc")
    List<Offer> findAllAvailableByShopIdAndProductId(Long shopId, Long productId);

    List<Offer> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Offer> findAllByCreatedAtBetweenAndPackShopId(LocalDateTime start, LocalDateTime end, Long shopId);

    List<Offer> findAllByCreatedAtBetweenAndPackLotProductId(LocalDateTime start, LocalDateTime end, Long productId);

    List<Offer> findAllByCreatedAtBetweenAndPackShopIdAndPackLotProductId(
            LocalDateTime start, LocalDateTime end, Long shopId, Long productId
    );
}


