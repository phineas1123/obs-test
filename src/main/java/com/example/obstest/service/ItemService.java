package com.example.obstest.service;

import com.example.obstest.exception.ValidationException;
import com.example.obstest.model.Item;
import org.springframework.data.domain.Page;

public interface ItemService {

    public Page<Item> getItemWithPagination(int pageNumber, int pageSize);

    Item getItemById(Long id) throws ValidationException;

    Item saveItem(Item item);

    Item patchItem(Long id, Item item);

    void deleteItem(Long id);
}
