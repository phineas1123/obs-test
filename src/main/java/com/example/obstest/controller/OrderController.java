package com.example.obstest.controller;

import com.example.obstest.dto.OrderDto;
import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Order;
import com.example.obstest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public Page<Order> getOrders(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrderWithPagination(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) throws ValidationException {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto dto) {
        Order savedOrder = orderService.saveOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDto dto)  throws  ValidationException{
        Order updatedOrder = orderService.patchOrder(id, dto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "DELETED");
        return response;
    }
}
