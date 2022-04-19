package com.i8ai.training.storeapi.repository;

import com.i8ai.training.storeapi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByRegisteredBetween(Date start, Date end);

    List<Sale> findAllByRegisteredBetweenAndPackShopId(Date start, Date end, Long shopId);

    List<Sale> findAllByRegisteredBetweenAndPackLotProductId(Date start, Date end, Long productId);

    List<Sale> findAllByRegisteredBetweenAndPackLotProductIdAndPackShopId(Date start, Date end, Long productId, Long shopId);

    @Query("select s.pack.amount - sum(s.amount) from Sale s where s.pack.id = :packId")
    Optional<Double> getRemainingAmountByPackId(Long packId);

    @Query("select sum(s.amount) from Sale s where s.pack.lot.product.id = :productId and s.pack.shop.id = :shopId")
    Optional<Double> getSoldAmountByProductIdAndShopId(Long productId, Long shopId);

    @Query("select sum(s.amount * s.price) from Sale s where s.registered between :start and :end")
    Optional<Double> getNetSalesIncome(Date start, Date end);

    @Query("select sum(s.amount * s.price) from Sale s where s.pack.lot.product.id = :productId " +
            "and s.registered between :start and :end")
    Optional<Double> getIncomeByProductId(Long productId, Date start, Date end);

    @Query("select sum(s.amount * s.price) from Sale s where s.pack.shop.id = :shopId " +
            "and s.registered between :start and :end")
    Optional<Double> getIncomeByShopId(Long shopId, Date start, Date end);

    @Query("select sum(s.amount * s.price) from Sale s where s.pack.lot.product.id = :productId " +
            "and s.pack.shop.id = :shopId and s.registered between :start and :end")
    Optional<Double> getIncomeByProductIdAndShopId(Long productId, Long shopId, Date start, Date end);

    @Query("select sum(s.amount * s.pack.lot.cost) from Sale s where s.registered between :start and :end")
    Optional<Double> getNetSalesExpenses(Date start, Date end);

    @Query("select sum(s.amount * s.pack.lot.cost) from Sale s where s.pack.lot.product.id = :productId " +
            "and s.registered between :start and :end")
    Optional<Double> getSaleExpensesByProductId(Long productId, Date start, Date end);

    @Query("select sum(s.amount * s.pack.lot.cost) from Sale s where s.pack.shop.id = :shopId " +
            "and s.registered between :start and :end")
    Optional<Double> getSaleExpensesByShopId(Long shopId, Date start, Date end);

    @Query("select sum(s.amount * s.pack.lot.cost) from Sale s where s.pack.lot.product.id = :productId " +
            "and s.pack.shop.id = :shopId and s.registered between :start and :end")
    Optional<Double> getSaleExpensesByProductIdAndShopId(Long productId, Long shopId, Date start, Date end);
}
