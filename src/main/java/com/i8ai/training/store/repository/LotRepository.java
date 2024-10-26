package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findAllByReceivedAtBetween(Date start, Date end);

    List<Lot> findAllByReceivedAtBetweenAndProductId(Date start, Date end, Long productId);

    @Query("select sum(l.acquiredAmount) from Lot l where l.product.id = :productId")
    Double getAmountArrivedByProductId(Long productId);

    @Modifying
    @Query("update Lot o set o.deliveredAmount = (select sum(p.receivedAmount) from Pack p where p.lot.id = :lotId) " +
            "where o.id = :lotId")
    void updateDeliveredAmountById(Long lotId);
}
