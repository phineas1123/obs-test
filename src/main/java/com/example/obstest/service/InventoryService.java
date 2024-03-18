package com.example.obstest.service;

import com.example.obstest.dto.InventoryDto;
import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Inventory;
import org.springframework.data.domain.Page;

public interface InventoryService {

    public Page<Inventory> getInventoryWithPagination(int pageNumber, int pageSize);

    Inventory getInventoryById(Long id) throws ValidationException;

    Inventory saveInventory(InventoryDto dto);

    Inventory patchInventory(Long id, InventoryDto dto);


    void deleteInventory(Long id);

    Long getTotalStock(Long itemId);

}
