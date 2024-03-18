package com.example.obstest.controller;

import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Item;
import com.example.obstest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/all")
    public Page<Item> getItems(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return itemService.getItemWithPagination(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) throws ValidationException {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item savedItem = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item)  throws  ValidationException{
            Item updatedItem = itemService.patchItem(id, item);
            return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "DELETED");
        return response;
    }
}
