package com.example.obstest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = true)
    public Item item;

    @NotNull(message = "Qty cannot be null")
    @Min(value = 0, message = "Qty must be greater than or equal to zero")
    public Integer qty;

    @NotNull(message = "Type cannot be null")
    @NotBlank(message = "Type cannot be null")
    public String type; //T= TOPUP, W = Withdrawal
}
