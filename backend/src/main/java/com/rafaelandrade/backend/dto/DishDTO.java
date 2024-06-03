package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.common.PortionSize;
import com.rafaelandrade.backend.entities.Category;
import com.rafaelandrade.backend.entities.Dish;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;

public class DishDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Double price;
    private PortionSize portionSize;
    private Duration preparationTime;
    private Category category;

    public DishDTO(){
    }

    public DishDTO(Long id, String name, String description, String imgUrl, Double price, PortionSize portionSize, Duration preparationTime, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.portionSize = portionSize;
        this.preparationTime = preparationTime;
        this.category = category;
    }

    public DishDTO(Dish dishEntity) {
        this.id = dishEntity.getId();
        this.name = dishEntity.getName();
        this.description = dishEntity.getDescription();
        this.imgUrl = dishEntity.getImgUrl();
        this.price = dishEntity.getPrice();
        this.portionSize = dishEntity.getPortionSize();
        this.preparationTime = dishEntity.getPreparationTime();
        this.category = dishEntity.getCategory();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PortionSize getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(PortionSize portionSize) {
        this.portionSize = portionSize;
    }

    public Duration getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Duration preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
