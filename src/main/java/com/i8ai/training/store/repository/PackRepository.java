package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

    List<Pack> findAllByReceivedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Pack> findAllByReceivedAtBetweenAndShopId(LocalDateTime start, LocalDateTime end, Long shopId);

    List<Pack> findAllByReceivedAtBetweenAndLotProductId(LocalDateTime start, LocalDateTime end, Long productId);

    List<Pack> findAllByReceivedAtBetweenAndLotProductIdAndShopId(LocalDateTime start, LocalDateTime end, Long productId, Long shopId);

    @Query("select p from Pack p where (p.soldAmount is null or p.receivedAmount - p.soldAmount > 0) " +
            "and p.shop.id = :shopId and p.lot.product.id = :productId order by p.receivedAt limit 1")
    Optional<Pack> findAvailableByShopIdAndProductId(Long shopId, Long productId);

    @Query("select sum(d.receivedAmount) from Pack d where d.lot.id = ?1")
    Double getDeliveredAmountByLotId(Long lotId);

    @Query("select sum(d.receivedAmount) from Pack d where d.lot.product.id = ?1")
    Double getDeliveredAmountByProductId(Long productId);

    @Query("select sum(d.receivedAmount) from Pack d where d.lot.product.id = ?1 and d.shop.id = ?2")
    Double getDeliveredAmountByProductIdAndShopId(Long productId, Long shopId);

    @Modifying
    @Query("update Pack p set p.soldAmount = (select sum(s.amount) from Sale s where s.offer.pack.id = :packId) " +
            "where p.id = :packId")
    void updateSoldAmountById(Long packId);
}
