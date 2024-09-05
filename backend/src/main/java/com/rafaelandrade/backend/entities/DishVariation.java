package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.dto.DishVariationDTO;
import com.rafaelandrade.backend.entities.common.PortionSize;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_dish_variation")
public class DishVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private PortionSize portionSize;
    private BigDecimal additionalPrice;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public DishVariation(){
    }

    public DishVariation(Long id, String sku, PortionSize portionSize, BigDecimal additionalPrice, Dish dish) {
        this.id = id;
        this.sku = sku;
        this.portionSize = portionSize;
        this.additionalPrice = additionalPrice;
        this.dish = dish;
    }

    public DishVariation(DishVariationDTO dishVariationDTO) {
        this.sku = dishVariationDTO.getSku();
        this.portionSize = dishVariationDTO.getPortionSize();
        this.additionalPrice = dishVariationDTO.getAdditionalPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public PortionSize getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(PortionSize portionSize) {
        this.portionSize = portionSize;
    }

    public BigDecimal getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishVariation that = (DishVariation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
