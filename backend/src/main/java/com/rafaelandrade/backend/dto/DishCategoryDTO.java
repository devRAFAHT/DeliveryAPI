package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.DishCategory;


public class DishCategoryDTO extends CategoryDTO {

    public DishCategoryDTO(DishCategory dishCategoryEntity) {
        this.setId(dishCategoryEntity.getId());
        this.setName(dishCategoryEntity.getName());
    }

}
