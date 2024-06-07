package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.DishCategory;
import com.rafaelandrade.backend.entities.DrinkCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public class DrinkCategoryDTO extends CategoryDTO{

    public DrinkCategoryDTO(DrinkCategory drinkCategoryEntity) {
        this.setId(drinkCategoryEntity.getId());
        this.setName(drinkCategoryEntity.getName());
    }

}
