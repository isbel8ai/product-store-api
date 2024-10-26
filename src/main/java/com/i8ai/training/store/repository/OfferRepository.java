package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    List<Offer> findAllByCreatedAtBetween(Date start, Date end);

    List<Offer> findAllByCreatedAtBetweenAndPackShopId(Date start, Date end, Long shopId);

    List<Offer> findAllByCreatedAtBetweenAndPackLotProductId(Date start, Date end, Long productId);

    List<Offer> findAllByCreatedAtBetweenAndPackShopIdAndPackLotProductId(
            Date start, Date end, Long shopId, Long productId
    );
}


