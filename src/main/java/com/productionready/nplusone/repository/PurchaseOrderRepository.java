package com.productionready.nplusone.repository;

import com.productionready.nplusone.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query("""
            select o from PurchaseOrder o
            left join fetch o.lines order by o.id
            """)
    List<PurchaseOrder> findAllWithLines();
}
