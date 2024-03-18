package com.example.obstest.service;

import com.example.obstest.dto.OrderDto;
import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    public Page<Order> getOrderWithPagination(int pageNumber, int pageSize);

    Order getOrderById(Long id) throws ValidationException;

    Order saveOrder(OrderDto dto);

    Order patchOrder(Long id, OrderDto dto);

    void deleteOrder(Long id);
}
