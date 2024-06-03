package com.project3.quanlynhahang.controller.admin;

import com.project3.quanlynhahang.dtos.reponse.LoginResponse;
import com.project3.quanlynhahang.dtos.request.LoginRequest;
import com.project3.quanlynhahang.service.AdminService;
import com.project3.quanlynhahang.utils.WebPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WebPath.ADMIN.API_ADMIN_AUTH_PATH)
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminService adminService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(adminService.login(request));
    }

}
