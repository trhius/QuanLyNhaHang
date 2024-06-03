package com.project3.quanlynhahang.controller.client;

import com.project3.quanlynhahang.dtos.reponse.OrderResponse;
import com.project3.quanlynhahang.dtos.request.CancelRequest;
import com.project3.quanlynhahang.dtos.request.OrderRequest;
import com.project3.quanlynhahang.dtos.request.RatingRequest;
import com.project3.quanlynhahang.service.ClientService;
import com.project3.quanlynhahang.utils.WebPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebPath.CLIENT.API_CLIENT_ORDER_PATH)
@RequiredArgsConstructor
public class OrderController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest request) {
        return ResponseEntity.ok(clientService.createOrder(request));
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Long orderId,
            @RequestBody CancelRequest request) {
        return ResponseEntity.ok(clientService.cancelOrder(orderId, request));
    }

    @PostMapping("/rate/{orderId}")
    public ResponseEntity<String> rateOrder(
            @PathVariable Long orderId,
            @RequestBody RatingRequest request) {
        return ResponseEntity.ok(clientService.rateOrder(orderId, request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderResponse>> listOrder(
            @RequestParam Long customerId) {
        return ResponseEntity.ok(clientService.getListOrder(customerId));
    }

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<String> payOrder(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(clientService.payOrder(orderId));
    }
}
