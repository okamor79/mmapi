package com.mmbeautyschool.mmapi.repository;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.Course;
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

    @Query("SELECT o FROM Sale o WHERE o.client=:client AND o.course=:course")
    List<Sale> getClentsOrder(@Param("client") Client client, @Param("course") Course course);

    @Query("SELECT o FROM Sale o WHERE o.client=:client AND o.course=:course AND o.payCheck=:check")
    Sale verifyPayCourse(@Param("client") Client client, @Param("course") Course course, @Param("check") boolean check);

    @Query("SELECT o FROM Sale o WHERE o.id = :id")
    Sale findById(@Param("id") long id);


}

