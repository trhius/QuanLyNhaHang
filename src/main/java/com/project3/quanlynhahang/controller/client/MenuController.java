package com.project3.quanlynhahang.controller.client;

import com.project3.quanlynhahang.entity.Food;
import com.project3.quanlynhahang.service.ClientService;
import com.project3.quanlynhahang.utils.WebPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebPath.CLIENT.API_CLIENT_MENU_PATH)
@RequiredArgsConstructor
public class MenuController {

    private final ClientService clientService;

    @GetMapping("/list")
    public ResponseEntity<List<Food>> listMenu() {
        return ResponseEntity.ok(clientService.getListMenu());
    }

}
