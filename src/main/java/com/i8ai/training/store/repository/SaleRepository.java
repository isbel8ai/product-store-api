package com.i8ai.training.store.repository;

import com.i8ai.training.store.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByRegisteredAtBetween(LocalDateTime start, LocalDateTime end);

    List<Sale> findAllByRegisteredAtBetweenAndOfferPackShopId(LocalDateTime start, LocalDateTime end, Long shopId);

    List<Sale> findAllByRegisteredAtBetweenAndOfferPackLotProductId(LocalDateTime start, LocalDateTime end, Long productId);

    List<Sale> findAllByRegisteredAtBetweenAndOfferPackLotProductIdAndOfferPackShopId(
            LocalDateTime start, LocalDateTime end, Long productId, Long shopId);

    @Query("select sum(s.amount) from Sale s where s.offer.pack.id = :packId")
    Double getSoldAmountByPackId(Long packId);

    @Query("select sum(s.amount) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.offer.pack.shop.id = :shopId")
    Double getSoldAmountByProductIdAndShopId(Long productId, Long shopId);

    @Query("select sum(s.amount * s.offer.price) from Sale s where s.registeredAt between :start and :end")
    Double getNetSalesIncome(LocalDateTime start, LocalDateTime end);

    @Query("select sum(s.amount * s.offer.price) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.registeredAt between :start and :end")
    Double getIncomeByProductId(LocalDateTime start, LocalDateTime end, Long productId);

    @Query("select sum(s.amount * s.offer.price) from Sale s where s.offer.pack.shop.id = :shopId " +
            "and s.registeredAt between :start and :end")
    Double getIncomeByShopId(LocalDateTime start, LocalDateTime end, Long shopId);

    @Query("select sum(s.amount * s.offer.price) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.offer.pack.shop.id = :shopId and s.registeredAt between :start and :end")
    Double getIncomeByProductIdAndShopId(LocalDateTime start, LocalDateTime end, Long productId, Long shopId);

    @Query("select sum(s.amount * s.offer.pack.lot.costPerUnit) from Sale s where s.registeredAt between :start and :end")
    Double getNetSalesExpenses(LocalDateTime start, LocalDateTime end);

    @Query("select sum(s.amount * s.offer.pack.lot.costPerUnit) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.registeredAt between :start and :end")
    Double getSaleExpensesByProductId(LocalDateTime start, LocalDateTime end, Long productId);

    @Query("select sum(s.amount * s.offer.pack.lot.costPerUnit) from Sale s where s.offer.pack.shop.id = :shopId " +
            "and s.registeredAt between :start and :end")
    Double getSaleExpensesByShopId(LocalDateTime start, LocalDateTime end, Long shopId);

    @Query("select sum(s.amount * s.offer.pack.lot.costPerUnit) from Sale s where s.offer.pack.lot.product.id = :productId " +
            "and s.offer.pack.shop.id = :shopId and s.registeredAt between :start and :end")
    Double getSaleExpensesByProductIdAndShopId(LocalDateTime start, LocalDateTime end, Long productId, Long shopId);
}
