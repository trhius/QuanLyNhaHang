package com.project3.quanlynhahang.controller.admin;

import com.project3.quanlynhahang.dtos.reponse.ReportResponse;
import com.project3.quanlynhahang.dtos.request.ReportRequest;
import com.project3.quanlynhahang.service.AdminService;
import com.project3.quanlynhahang.utils.WebPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebPath.ADMIN.API_ADMIN_REPORT_PATH)
@RequiredArgsConstructor
public class ReportController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ReportResponse> getReport(
            @RequestBody(required = false) ReportRequest request) {
        return ResponseEntity.ok(adminService.getReport(request));
    }

}
