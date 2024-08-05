package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.PortionSize;
import com.rafaelandrade.backend.entities.common.SaleStatus;
import com.rafaelandrade.backend.entities.common.UnitMeasurement;
import jakarta.persistence.*;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_dish")
public class Dish extends Food{
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer portionSize;

    @ManyToOne
    @JoinColumn(name = "dish_category_id")
    private DishCategory category;

    @ManyToMany()
    @JoinTable(name = "tb_dish_additional", joinColumns = @JoinColumn(name = "dish_id"), inverseJoinColumns = @JoinColumn(name = "additional_id"))
    private Set<Additional> additional = new HashSet<>();

    public Dish(){
    }

    public Dish(PortionSize portionSize, DishCategory category) {
        setPortionSize(portionSize);
        this.category = category;
    }

    public Dish(Integer foodRestriction, Integer portionSize, DishCategory category) {
        super(foodRestriction);
        this.portionSize = portionSize;
        this.category = category;
    }

    public Dish(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage, Integer size, UnitMeasurement unitMeasurement, SaleStatus saleStatus, Menu menu, Integer foodRestriction, Integer portionSize, DishCategory category) {
        super(id, name, description, imgUrl, originalPrice, currentPrice, discountInPercentage, size, unitMeasurement, saleStatus, menu, foodRestriction);
        this.portionSize = portionSize;
        this.category = category;
    }

    public PortionSize getPortionSize() {
        return PortionSize.valueOf(portionSize);
    }

    public void setPortionSize(PortionSize portionSize) {
        if (portionSize != null) {
            this.portionSize = portionSize.getCode();
        }
    }

    public DishCategory getCategory() {
        return category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public Set<Additional> getAdditional() {
        return additional;
    }
}
