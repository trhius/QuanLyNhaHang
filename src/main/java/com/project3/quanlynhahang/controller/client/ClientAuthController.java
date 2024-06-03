package com.project3.quanlynhahang.controller.client;

import com.project3.quanlynhahang.dtos.reponse.LoginResponse;
import com.project3.quanlynhahang.dtos.request.LoginRequest;
import com.project3.quanlynhahang.dtos.request.RegisterRequest;
import com.project3.quanlynhahang.dtos.request.UpdateInfoRequest;
import com.project3.quanlynhahang.service.ClientService;
import com.project3.quanlynhahang.utils.WebPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WebPath.CLIENT.API_CLIENT_AUTH_PATH)
@RequiredArgsConstructor
public class ClientAuthController {

    private final ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(clientService.register(request));
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(clientService.login(request));
    }

    @PostMapping("/update-info/{customerId}")
    public ResponseEntity<String> updateInfo(
            @RequestBody UpdateInfoRequest request,
            @PathVariable Long customerId) {
        return ResponseEntity.ok(clientService.updateInfo(customerId, request));
    }

}
