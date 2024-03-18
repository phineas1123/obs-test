package com.example.obstest.service;

import com.example.obstest.dto.InventoryDto;
import com.example.obstest.dto.OrderDto;
import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Item;
import com.example.obstest.model.Order;
import com.example.obstest.repository.OrderRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;

    ItemService itemService;

    InventoryService inventoryService;

    private final Validator validator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ItemService itemService,
                            InventoryService inventoryService,
                            Validator validator) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.inventoryService = inventoryService;
        this.validator = validator;
    }

    @Override
    public Page<Order> getOrderWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getOrderById(Long id) throws ValidationException {
        Order order = orderRepository.findById(id).orElse(null);

        if(order.equals(null)) throw new ValidationException("DATA_NOT_FOUND");

        return order;
    }

    @Override
    public Order saveOrder(OrderDto dto) {
        // validate dto
        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (ConstraintViolation<OrderDto> violation : violations) {
                errorMessage.append(",").append(violation.getMessage());
            }
            throw new ValidationException(errorMessage.toString());
        }

        //check if item exists
        Item item = itemService.getItemById(dto.itemId);

        //get current stock (Topup - Withdraw)
        Long currentStock = inventoryService.getTotalStock(dto.itemId);
        if(currentStock < dto.qty) new ValidationException("NOT_ENOUGH_STOCK");

        // create order
        Order order = new Order();
        order.item = item;
        order.orderNo = dto.orderNo;
        order.qty = dto.qty;
        Order createdOrder =  orderRepository.save(order);

        // insert new inventory stock (for withdraw)
        InventoryDto inventory = new InventoryDto();
        inventory.qty = dto.qty;
        inventory.type = "W";
        inventory.itemId = dto.itemId;

        inventoryService.saveInventory(inventory);
        return createdOrder;
    }

    @Override
    public Order patchOrder(Long id, OrderDto dto) {
        // validate dto
        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (ConstraintViolation<OrderDto> violation : violations) {
                errorMessage.append(",").append(violation.getMessage());
            }
            throw new ValidationException(errorMessage.toString());
        }

        //check if item exists
        Item item = itemService.getItemById(dto.itemId);

        //check if order exists
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null) throw new ValidationException("DATA_NOT_FOUND");

        //count diff stock (current qty -
        Integer diffStock = order.qty - dto.qty;

        Boolean isWithDraw = false;
        //if there's increment stock (diffStock > 0) then withdraw inventory again, else topup (return)
        if(diffStock > 0) isWithDraw = true;

        //get current stock (Topup - Withdraw)
        Long currentStock = inventoryService.getTotalStock(dto.itemId);

        //if withdraw, check if diff stock or new stock is enough for current stock (current stock > diff stock)
        if(isWithDraw)
            if(currentStock < diffStock) new ValidationException("NOT_ENOUGH_STOCK");

        // update order
        order.item = item;
        order.orderNo = dto.orderNo;
        order.qty = dto.qty;
        Order updateOrder =  orderRepository.save(order);

        //insert inventory if there's gap (withdraw/topup) -> if 0 diff, dont insert into new inventory
        if(diffStock != 0) {
            InventoryDto inventory = new InventoryDto();
            inventory.qty = Math.abs(diffStock);
            //if isWithdraw = true then withdraw, else topup
            if (isWithDraw)
                inventory.type = "W";
            else
                inventory.type = "T";
            inventory.itemId = dto.itemId;

            inventoryService.saveInventory(inventory);
        }

        return updateOrder;
    }


    @Override
    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id).orElse(null);
        if(order.equals(null)) throw new ValidationException("DATA_NOT_FOUND");

        //insert inventory topup/ add new stock
        InventoryDto inventory = new InventoryDto();
        inventory.qty = order.qty;
        inventory.type = "T";
        inventory.itemId =order.item.id;
        inventoryService.saveInventory(inventory);

        orderRepository.deleteById(id);
    }
}
