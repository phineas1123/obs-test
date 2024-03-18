package com.example.obstest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String orderNo;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = true)
    public Item item;

    @NotNull(message = "Qty cannot be null")
    @Min(value = 0, message = "Qty must be greater than or equal to zero")
    public Integer qty;
}
