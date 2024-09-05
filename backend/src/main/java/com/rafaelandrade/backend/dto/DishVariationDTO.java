package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.DishVariation;
import com.rafaelandrade.backend.entities.common.PortionSize;

import java.math.BigDecimal;

public class DishVariationDTO {

    private int id;
    private String sku;
    private PortionSize portionSize;
    private BigDecimal additionalPrice;
    private DishDTO dishDTO;

    public DishVariationDTO(){
    }

    public DishVariationDTO(String sku, PortionSize portionSize, BigDecimal additionalPrice) {
        this.sku = sku;
        this.portionSize = portionSize;
        this.additionalPrice = additionalPrice;
    }

    public DishVariationDTO(String sku, PortionSize portionSize, BigDecimal additionalPrice, DishDTO dishDTO) {
        this.sku = sku;
        this.portionSize = portionSize;
        this.additionalPrice = additionalPrice;
        this.dishDTO = dishDTO;
    }

    public DishVariationDTO(DishVariation dishVariation) {
        this.sku = dishVariation.getSku();
        this.portionSize = dishVariation.getPortionSize();
        this.additionalPrice = dishVariation.getAdditionalPrice();
    }

    public int getId() {
        return id;
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

    public DishDTO getDishDTO() {
        return dishDTO;
    }

    public void setDishDTO(DishDTO dishDTO) {
        this.dishDTO = dishDTO;
    }
}
