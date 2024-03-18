package com.example.obstest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be null")
    public String name;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to zero")
    public Double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<Inventory> inventories = new ArrayList<>();

}
