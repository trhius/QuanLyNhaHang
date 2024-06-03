package com.project3.quanlynhahang.controller.admin;

import com.project3.quanlynhahang.dtos.request.FoodRequest;
import com.project3.quanlynhahang.entity.Food;
import com.project3.quanlynhahang.service.AdminService;
import com.project3.quanlynhahang.utils.WebPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebPath.ADMIN.API_ADMIN_MENU_PATH)
@RequiredArgsConstructor
public class MenuManagementController {

    private final AdminService adminService;

    @GetMapping("/list")
    public ResponseEntity<List<Food>> listMenu() {
        return ResponseEntity.ok(adminService.getListMenu());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFood(
            @RequestBody FoodRequest request) {
        return ResponseEntity.ok(adminService.addFood(request));
    }

    @PostMapping("/update/{foodId}")
    public ResponseEntity<String> updateFood(
            @RequestBody FoodRequest request,
            @PathVariable Long foodId) {
        return ResponseEntity.ok(adminService.updateFood(request, foodId));
    }

    @PostMapping("delete/{foodId}")
    public ResponseEntity<String> deleteFood(
            @PathVariable Long foodId) {
        return ResponseEntity.ok(adminService.deleteFood(foodId));
    }

}
