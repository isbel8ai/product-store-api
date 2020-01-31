package com.i8ai.training.storeapi.repository;

import com.i8ai.training.storeapi.domain.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    List<Pack> findAllByDeliveredBetweenAndLotProductIdAndShopId(Date start, Date end, Long productId, Long shopId);

    List<Pack> findAllByDeliveredBetweenAndLotProductId(Date start, Date end, Long productId);

    List<Pack> findAllByDeliveredBetweenAndShopId(Date start, Date end, Long shopId);

    List<Pack> findAllByDeliveredBetween(Date start, Date end);

    @Query("select sum(d.amount) from Pack d where d.lot.id = ?1")
    Double getDeliveredAmountByLotId(Long lotId);

    @Query("select sum(d.amount) from Pack d where d.lot.product.id = ?1")
    Double getDeliveredAmountByProductId(Long productId);

    @Query("select sum(d.amount) from Pack d where d.lot.product.id = ?1 and d.shop.id = ?2")
    Double getDeliveredAmountByProductIdAndShopId(Long productId, Long shopId);
}
