package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.RestaurantCategory;

import java.io.Serial;

public class RestaurantCategoryDTO extends CategoryDTO{
    @Serial
    private static final long serialVersionUID = 1L;

    public RestaurantCategoryDTO(){
    }

    public RestaurantCategoryDTO(RestaurantCategory restaurantCategoryEntity) {
        this.setId(restaurantCategoryEntity.getId());
        this.setName(restaurantCategoryEntity.getName());
    }

}
