package com.project3.quanlynhahang.service;

import com.project3.quanlynhahang.dtos.reponse.LoginResponse;
import com.project3.quanlynhahang.dtos.reponse.ReportResponse;
import com.project3.quanlynhahang.dtos.request.*;
import com.project3.quanlynhahang.entity.Employee;
import com.project3.quanlynhahang.entity.Food;

import java.util.List;

public interface AdminService {
    LoginResponse login(LoginRequest request);

    String createAccount(CreateAccountRequest request);

    List<Food> getListMenu();

    String addFood(FoodRequest request);

    String updateFood(FoodRequest request, Long foodId);

    String deleteFood(Long foodId);

    List<Employee> getListEmployee();

    String updateEmployee(Long employeeId, UpdateInfoRequest request);

    String deleteEmployee(Long employeeId);

    String updateCustomerStatus(UpdateStatusRequest request);

    ReportResponse getReport(ReportRequest request);

}
