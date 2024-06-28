package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.RestaurantCategory;

public class RestaurantCategoryDTO extends CategoryDTO{

    public RestaurantCategoryDTO(){
    }

    public RestaurantCategoryDTO(RestaurantCategory restaurantCategoryEntity) {
        this.setId(restaurantCategoryEntity.getId());
        this.setName(restaurantCategoryEntity.getName());
    }

}
