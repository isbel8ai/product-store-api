package com.i8ai.training.storeapi.repository;

import com.i8ai.training.storeapi.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findAllByReceivedBetween(Date start, Date end);

    List<Lot> findAllByReceivedBetweenAndProductId(Date start, Date end, Long productId);

    @Query("select sum(l.amount) from Lot l where l.product.id = ?1")
    Double getAmountArrivedByProductId(Long productId);
}
