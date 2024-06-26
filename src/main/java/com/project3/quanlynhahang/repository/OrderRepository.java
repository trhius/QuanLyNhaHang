package com.project3.quanlynhahang.repository;

import com.project3.quanlynhahang.dtos.request.ReportRequest;
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

    @Query("""
        SELECT o FROM Orders o
        JOIN Customer c ON o.customerId = c.id
        JOIN Employee e ON o.shipperId = e.id
        WHERE (:#{#request.fromDate} IS NULL OR :#{#request.fromDate} <= o.createdAt)
        AND (:#{#request.toDate} IS NULL OR :#{#request.toDate} >= o.createdAt)
        AND (:#{#request.transactionStatus} IS NULL OR :#{#request.transactionStatus} = o.transactionStatus)
        AND (:#{#request.shippingStatus} IS NULL OR :#{#request.shippingStatus} = o.shippingStatus)
        AND (:#{#request.searchByCustomerName} IS NULL OR LOWER(c.fullName) LIKE LOWER(CONCAT('%', :#{#request.searchByCustomerName}, '%')))
        AND (:#{#request.searchByShipperName} IS NULL OR LOWER(e.fullName) LIKE LOWER(CONCAT('%', :#{#request.searchByShipperName}, '%')))
    """)
    List<Orders> findOrderReport(ReportRequest request);
}


