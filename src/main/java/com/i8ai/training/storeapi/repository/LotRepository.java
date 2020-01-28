package com.i8ai.training.storeapi.repository;

import com.i8ai.training.storeapi.domain.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findAllByReceivedBetween(Date startDate, Date endDate);

    List<Lot> findAllByReceivedBetweenAndProductId(Date startDate, Date endDate, Long productId);
}
