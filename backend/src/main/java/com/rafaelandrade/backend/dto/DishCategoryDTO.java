package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.DishCategory;

import java.io.Serial;

public class DishCategoryDTO extends CategoryDTO{
    @Serial
    private static final long serialVersionUID = 1L;

    public DishCategoryDTO(){
    }

    public DishCategoryDTO(DishCategory dishCategoryEntity) {
        this.setId(dishCategoryEntity.getId());
        this.setName(dishCategoryEntity.getName());
    }

}
