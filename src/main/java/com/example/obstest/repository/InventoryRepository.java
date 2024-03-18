package com.example.obstest.repository;

import com.example.obstest.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query(value = "SELECT SUM(qty) FROM INVENTORY WHERE ITEM_ID = ?1", nativeQuery = true)
    Long countWithdrawStock(Long itemId);

    @Query(value = "SELECT SUM(qty) FROM INVENTORY WHERE ITEM_ID = ?1", nativeQuery = true)
    Long countTopupStock(Long itemId);
}
