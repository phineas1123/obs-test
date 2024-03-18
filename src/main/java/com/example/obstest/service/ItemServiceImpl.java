package com.example.obstest.service;

import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Item;
import com.example.obstest.repository.ItemRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService{
    ItemRepository itemRepository;

    private final Validator validator;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, Validator validator) {
        this.itemRepository = itemRepository;
        this.validator = validator;
    }

    @Override
    public Page<Item> getItemWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return itemRepository.findAll(pageable);
    }

    @Override
    public Item getItemById(Long id) throws ValidationException {
        Item item = itemRepository.findById(id).orElse(null);

        if(item.equals(null)) throw new ValidationException("DATA_NOT_FOUND");

        return item;
    }

    @Override
    public Item saveItem(Item item) {
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (ConstraintViolation<Item> violation : violations) {
                errorMessage.append(",").append(violation.getMessage());
            }
            throw new ValidationException(errorMessage.toString());
        }
        return itemRepository.save(item);
    }

    @Override
    public Item patchItem(Long id, Item item) {
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (ConstraintViolation<Item> violation : violations) {
                errorMessage.append(",").append(violation.getMessage());
            }
            throw new ValidationException(errorMessage.toString());
        }
        Item currentItem = itemRepository.findById(id).orElse(null);
        if(currentItem.equals(null)) throw new ValidationException("DATA_NOT_FOUND");

        item.id = id;
        return itemRepository.save(item);
    }



    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
