package com.project3.quanlynhahang.repository;

import com.project3.quanlynhahang.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrdersId(Long orderId);

}
