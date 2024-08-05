package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.SaleStatus;
import com.rafaelandrade.backend.entities.common.UnitMeasurement;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_drink")
public class Drink extends Food{
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "drink_category_id")
    private DrinkCategory category;

    public Drink(){
    }

    public Drink(DrinkCategory category) {
        this.category = category;
    }

    public Drink(Integer foodRestriction, DrinkCategory category) {
        super(foodRestriction);
        this.category = category;
    }

    public Drink(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage, Integer size, UnitMeasurement unitMeasurement, SaleStatus saleStatus, Menu menu, Integer foodRestriction, DrinkCategory category) {
        super(id, name, description, imgUrl, originalPrice, currentPrice, discountInPercentage, size, unitMeasurement, saleStatus, menu, foodRestriction);
        this.category = category;
    }

    public DrinkCategory getCategory() {
        return category;
    }

    public void setCategory(DrinkCategory category) {
        this.category = category;
    }
}
