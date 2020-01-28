package com.i8ai.training.storeapi.repository;

import com.i8ai.training.storeapi.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByDeliveredBetweenAndFromProductIdAndToId(Date start, Date end, Long productId, Long shopId);

    List<Delivery> findAllByDeliveredBetweenAndFromProductId(Date start, Date end, Long productId);

    List<Delivery> findAllByDeliveredBetweenAndToId(Date start, Date end, Long shopId);

    List<Delivery> findAllByDeliveredBetween(Date start, Date end);
}
