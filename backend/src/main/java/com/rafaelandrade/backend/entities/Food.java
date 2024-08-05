package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.FoodRestriction;
import com.rafaelandrade.backend.entities.common.SaleStatus;
import com.rafaelandrade.backend.entities.common.UnitMeasurement;
import jakarta.persistence.MappedSuperclass;

import java.math.BigDecimal;

@MappedSuperclass
public abstract class Food extends Item{

    private Integer foodRestriction;

    public Food(){
    }

    public Food(Integer foodRestriction) {
        this.foodRestriction = foodRestriction;
    }

    public Food(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage, Integer size, UnitMeasurement unitMeasurement, SaleStatus saleStatus, Menu menu, Integer foodRestriction) {
        super(id, name, description, imgUrl, originalPrice, currentPrice, discountInPercentage, size, unitMeasurement, saleStatus, menu);
        this.foodRestriction = foodRestriction;
    }

    public FoodRestriction getFoodRestriction() {
        return FoodRestriction.valueOf(foodRestriction);
    }

    public void setFoodRestriction(FoodRestriction foodRestriction) {
        if (foodRestriction != null) {
            this.foodRestriction = foodRestriction.getCode();
        }
    }
}
