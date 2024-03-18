package com.example.obstest.controller;

import com.example.obstest.dto.InventoryDto;
import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Inventory;
import com.example.obstest.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/all")
    public Page<Inventory> getInventorys(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return inventoryService.getInventoryWithPagination(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) throws ValidationException {
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryDto dto) {
        Inventory savedInventory = inventoryService.saveInventory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody InventoryDto dto)  throws  ValidationException{
        Inventory updatedInventory = inventoryService.patchInventory(id, dto);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "DELETED");
        return response;
    }

    @GetMapping("/stock/{itemId}")
    public ResponseEntity<Long> getTotalStock(@PathVariable Long itemId) throws ValidationException {
        Long totalStock = inventoryService.getTotalStock(itemId);
        return ResponseEntity.ok(totalStock);
    }
}
