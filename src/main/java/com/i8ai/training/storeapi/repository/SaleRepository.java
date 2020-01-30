package com.i8ai.training.storeapi.repository;

import com.i8ai.training.storeapi.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByRegisteredBetween(Date start, Date end);

    List<Sale> findAllByRegisteredBetweenAndPackShopId(Date start, Date end, Long shopId);

    List<Sale> findAllByRegisteredBetweenAndPackLotProductId(Date start, Date end, Long productId);

    List<Sale> findAllByRegisteredBetweenAndPackLotProductIdAndPackShopId(Date start, Date end, Long productId, Long shopId);

    @Query("select sum(s.amount) from Sale s where s.pack.id = ?1")
    Double getSoldAmountByPackId(Long packId);
}
