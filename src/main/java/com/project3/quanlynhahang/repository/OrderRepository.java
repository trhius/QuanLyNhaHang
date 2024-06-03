package com.project3.quanlynhahang.repository;

import com.project3.quanlynhahang.entity.Orders;
import com.project3.quanlynhahang.enums.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT DISTINCT o.shipperId FROM Orders o WHERE o.shippingStatus NOT IN (:excludedStatuses)")
    List<Long> findUnavailableShipperIds(List<ShippingStatus> excludedStatuses);

    List<Orders> findAllByCustomerId(Long customerId);

    @Query("SELECT o FROM Orders o ORDER BY o.customerId")
    List<Orders> findOrderListSorted();
}


