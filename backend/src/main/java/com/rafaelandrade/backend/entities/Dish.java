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

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DishVariation> variations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "dish_category_id")
    private DishCategory category;

    @ManyToMany()
    @JoinTable(name = "tb_dish_additional", joinColumns = @JoinColumn(name = "dish_id"), inverseJoinColumns = @JoinColumn(name = "additional_id"))
    private Set<Additional> additional = new HashSet<>();

    public Dish(){
    }

    public Dish(DishCategory category) {
        this.category = category;
    }

    public Dish(Integer foodRestriction, DishCategory category) {
        super(foodRestriction);
        this.category = category;
    }

    public Dish(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage, Integer size, UnitMeasurement unitMeasurement, SaleStatus saleStatus, Menu menu, Integer foodRestriction, DishCategory category) {
        super(id, name, description, imgUrl, originalPrice, currentPrice, discountInPercentage, size, unitMeasurement, saleStatus, menu, foodRestriction);
        this.category = category;
    }

    public Set<DishVariation> getVariations() {
        return variations;
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
