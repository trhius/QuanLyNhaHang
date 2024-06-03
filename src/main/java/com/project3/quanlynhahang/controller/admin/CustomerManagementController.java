package com.project3.quanlynhahang.controller.admin;

import com.project3.quanlynhahang.dtos.request.UpdateStatusRequest;
import com.project3.quanlynhahang.entity.Customer;
import com.project3.quanlynhahang.entity.Orders;
import com.project3.quanlynhahang.repository.CustomerRepository;
import com.project3.quanlynhahang.repository.OrderRepository;
import com.project3.quanlynhahang.service.AdminService;
import com.project3.quanlynhahang.utils.WebPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebPath.ADMIN.API_ADMIN_CUSTOMER_PATH)
@RequiredArgsConstructor
public class CustomerManagementController {

    private final AdminService adminService;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> getCustomerList() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping("/order-list")
    public ResponseEntity<List<Orders>> getOrderList() {
        return ResponseEntity.ok(orderRepository.findOrderListSorted());
    }

    @PostMapping("/update-status")
    public ResponseEntity<String> updateCustomerStatus(
            @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(adminService.updateCustomerStatus(request));
    }

}
