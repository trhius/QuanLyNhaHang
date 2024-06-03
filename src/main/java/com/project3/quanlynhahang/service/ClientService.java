package com.project3.quanlynhahang.service;

import com.project3.quanlynhahang.dtos.reponse.LoginResponse;
import com.project3.quanlynhahang.dtos.reponse.OrderResponse;
import com.project3.quanlynhahang.dtos.request.*;
import com.project3.quanlynhahang.entity.Food;

import java.util.List;

public interface ClientService {
    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    String updateInfo(Long customerId, UpdateInfoRequest request);

    List<Food> getListMenu();

    OrderResponse createOrder(OrderRequest request);

    String cancelOrder(Long orderId, CancelRequest request);

    String rateOrder(Long orderId, RatingRequest request);

    List<OrderResponse> getListOrder(Long customerId);

    String payOrder(Long orderId);
}
