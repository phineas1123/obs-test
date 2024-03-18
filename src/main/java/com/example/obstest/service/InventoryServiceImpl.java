package com.example.obstest.service;

import com.example.obstest.dto.InventoryDto;
import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Inventory;
import com.example.obstest.model.Item;
import com.example.obstest.repository.InventoryRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InventoryServiceImpl implements InventoryService {
    InventoryRepository inventoryRepository;

    ItemService itemService;

    private final Validator validator;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                ItemService itemService,
                                Validator validator) {
        this.inventoryRepository = inventoryRepository;
        this.itemService = itemService;
        this.validator = validator;
    }

    @Override
    public Page<Inventory> getInventoryWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public Inventory getInventoryById(Long id) throws ValidationException {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);

        if(inventory.equals(null)) throw new ValidationException("DATA_NOT_FOUND");

        return inventory;
    }

    @Override
    public Inventory saveInventory(InventoryDto dto) {
        Set<ConstraintViolation<InventoryDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (ConstraintViolation<InventoryDto> violation : violations) {
                errorMessage.append(",").append(violation.getMessage());
            }
            throw new ValidationException(errorMessage.toString());
        }

        //check if item exists
        Item item = itemService.getItemById(dto.itemId);

        Inventory inventory = new Inventory();
        inventory.type = dto.type;
        inventory.qty = dto.qty;
        inventory.item = item;

        Inventory createInventory = inventoryRepository.save(inventory);

        return createInventory;

    }

    @Override
    public Inventory patchInventory(Long id, InventoryDto dto) {
        Set<ConstraintViolation<InventoryDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (ConstraintViolation<InventoryDto> violation : violations) {
                errorMessage.append(",").append(violation.getMessage());
            }
            throw new ValidationException(errorMessage.toString());
        }

        //check if item exists
        Item item = itemService.getItemById(dto.itemId);

        //check if inventory exists
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if(inventory == null) throw new ValidationException("DATA_NOT_FOUND");
        inventory.type = dto.type;
        inventory.qty = dto.qty;
        inventory.item = item;

        Inventory createInventory = inventoryRepository.save(inventory);

        return createInventory;

    }



    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if(inventory.equals(null)) throw new ValidationException("DATA_NOT_FOUND");

        inventoryRepository.deleteById(id);
    }

    @Override
    public Long getTotalStock(Long itemId) {
        // get topup and withdraw stock
        Long withDrawStock = inventoryRepository.countWithdrawStock(itemId);
        Long topupStock = inventoryRepository.countTopupStock(itemId);

        // totalStock = topup stock - withdraw stocks
        Long totalStock = topupStock - withDrawStock;

        return totalStock;
    }
}
