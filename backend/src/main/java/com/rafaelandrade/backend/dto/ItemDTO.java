package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.Item;
import com.rafaelandrade.backend.entities.common.SaleStatus;
import com.rafaelandrade.backend.entities.common.UnitMeasurement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemDTO {

    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private BigDecimal discountInPercentage;
    private Integer size;
    private UnitMeasurement unitMeasurement;
    private SaleStatus saleStatus;

    public ItemDTO(){
    }

    public ItemDTO(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage, Integer size, UnitMeasurement unitMeasurement, SaleStatus saleStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.discountInPercentage = discountInPercentage;
        this.size = size;
        this.unitMeasurement = unitMeasurement;
        this.saleStatus = saleStatus;
    }

    public ItemDTO(Item itemEntity) {
        this.id = itemEntity.getId();
        this.name = itemEntity.getName();
        this.description = itemEntity.getDescription();
        this.imgUrl = itemEntity.getImgUrl();
        this.originalPrice = itemEntity.getOriginalPrice();
        this.currentPrice = itemEntity.getCurrentPrice();
        this.discountInPercentage = itemEntity.getDiscountInPercentage();
        this.size = itemEntity.getSize();
        this.unitMeasurement = itemEntity.getUnitMeasurement();
        this.saleStatus = itemEntity.getSaleStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getDiscountInPercentage() {
        return discountInPercentage;
    }

    public void setDiscountInPercentage(BigDecimal discountInPercentage) {
        this.discountInPercentage = discountInPercentage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public UnitMeasurement getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(UnitMeasurement unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }
}
