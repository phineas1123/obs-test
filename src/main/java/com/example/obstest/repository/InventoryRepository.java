package com.example.obstest.repository;

import com.example.obstest.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query(value = "SELECT COALESCE(SUM(qty), 0) FROM inventories WHERE ITEM_ID = ?1 AND TYPE = 'W' ", nativeQuery = true)
    Long countWithdrawStock(Long itemId);

    @Query(value = "SELECT COALESCE(SUM(qty), 0) FROM inventories WHERE ITEM_ID = ?1 AND TYPE = 'T' ", nativeQuery = true)
    Long countTopupStock(Long itemId);
}
