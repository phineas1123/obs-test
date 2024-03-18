package com.example.obstest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InventoryDto {

    @NotNull(message = "Item Id cannot be null")
    public Long itemId;

    @NotNull(message = "Qty cannot be null")
    @Min(value = 0, message = "Qty must be greater than or equal to zero")
    public Integer qty;

    @NotNull(message = "Type cannot be null")
    @NotBlank(message = "Type cannot be null")
    public String type;
}
