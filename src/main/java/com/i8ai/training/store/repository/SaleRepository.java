package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByRegisteredAtBetween(Date start, Date end);

    List<Sale> findAllByRegisteredAtBetweenAndOfferPackShopId(Date start, Date end, Long shopId);

    List<Sale> findAllByRegisteredAtBetweenAndOfferPackLotProductId(Date start, Date end, Long productId);

    List<Sale> findAllByRegisteredAtBetweenAndOfferPackLotProductIdAndOfferPackShopId(
            Date start, Date end, Long productId, Long shopId);

    @Query("select sum(s.amount) from Sale s where s.offer.pack.id = :packId")
    Double getSoldAmountByPackId(Long packId);

    @Query("select sum(s.amount) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.offer.pack.shop.id = :shopId")
    Double getSoldAmountByProductIdAndShopId(Long productId, Long shopId);

    @Query("select sum(s.amount * s.offer.price) from Sale s where s.registeredAt between :start and :end")
    Double getNetSalesIncome(Date start, Date end);

    @Query("select sum(s.amount * s.offer.price) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.registeredAt between :start and :end")
    Double getIncomeByProductId(Date start, Date end, Long productId);

    @Query("select sum(s.amount * s.offer.price) from Sale s where s.offer.pack.shop.id = :shopId " +
            "and s.registeredAt between :start and :end")
    Double getIncomeByShopId(Date start, Date end, Long shopId);

    @Query("select sum(s.amount * s.offer.price) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.offer.pack.shop.id = :shopId and s.registeredAt between :start and :end")
    Double getIncomeByProductIdAndShopId(Date start, Date end, Long productId, Long shopId);

    @Query("select sum(s.amount * s.offer.pack.lot.costPerUnit) from Sale s where s.registeredAt between :start and :end")
    Double getNetSalesExpenses(Date start, Date end);

    @Query("select sum(s.amount * s.offer.pack.lot.costPerUnit) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.registeredAt between :start and :end")
    Double getSaleExpensesByProductId(Date start, Date end, Long productId);

    @Query("select sum(s.amount * s.offer.pack.lot.costPerUnit) from Sale s where s.offer.pack.shop.id = :shopId " +
            "and s.registeredAt between :start and :end")
    Double getSaleExpensesByShopId(Date start, Date end, Long shopId);

    @Query("select sum(s.amount * s.offer.pack.lot.costPerUnit) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.offer.pack.shop.id = :shopId and s.registeredAt between :start and :end")
    Double getSaleExpensesByProductIdAndShopId(Date start, Date end, Long productId, Long shopId);
}
