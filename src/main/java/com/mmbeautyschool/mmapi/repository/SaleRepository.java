package com.mmbeautyschool.mmapi.repository;

import com.mmbeautyschool.mmapi.entity.Sale;
import com.mmbeautyschool.mmapi.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT o FROM Sale o WHERE o.status in :status")
    List<Sale> getUnpaidOrders(@Param("status") Collection<OrderStatus> statuses);
}

