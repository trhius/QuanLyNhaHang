package com.project3.quanlynhahang.controller.admin;

import com.project3.quanlynhahang.dtos.request.CreateAccountRequest;
import com.project3.quanlynhahang.dtos.request.UpdateInfoRequest;
import com.project3.quanlynhahang.dtos.request.UpdateStatusRequest;
import com.project3.quanlynhahang.entity.Employee;
import com.project3.quanlynhahang.service.AdminService;
import com.project3.quanlynhahang.utils.WebPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebPath.ADMIN.API_ADMIN_EMPLOYEE_PATH)
@RequiredArgsConstructor
public class EmployeeManagementController {

    private final AdminService adminService;

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getListEmployee() {
        return ResponseEntity.ok(adminService.getListEmployee());
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> create(
            @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(adminService.createAccount(request));
    }

    @PostMapping("/update/{employeeId}")
    public ResponseEntity<String> update(
            @RequestBody UpdateInfoRequest request,
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(adminService.updateEmployee(employeeId, request));
    }

    @PostMapping("/delete/{employeeId}")
    public ResponseEntity<String> delete(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(adminService.deleteEmployee(employeeId));
    }

}
