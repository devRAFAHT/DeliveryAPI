package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.common.PortionSize;
import com.rafaelandrade.backend.entities.DishCategory;
import com.rafaelandrade.backend.entities.Dish;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;

public class DishDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 60, message = "O nome dever ter no máximo 60 caracteres")
    private String name;
    @Size(max = 512, message = "A descrição deve ter no máximo 512 caracteres")
    private String description;
    @NotBlank(message = "Campo obrigatório")
    private String imgUrl;
    @NotBlank(message = "Campo obrigatório")
    @Positive(message = "O preço deve ser positivo")
    private BigDecimal price;
    private PortionSize portionSize;
    @NotBlank(message = "Campo obrigatório")
    private Duration preparationTime;
    @NotBlank(message = "Campo obrigatório")
    private DishCategory dishCategory;

    public DishDTO(){
    }

    public DishDTO(Long id, String name, String description, String imgUrl, BigDecimal price, PortionSize portionSize, Duration preparationTime, DishCategory dishCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.portionSize = portionSize;
        this.preparationTime = preparationTime;
        this.dishCategory = dishCategory;
    }

    public DishDTO(Dish dishEntity) {
        this.id = dishEntity.getId();
        this.name = dishEntity.getName();
        this.description = dishEntity.getDescription();
        this.imgUrl = dishEntity.getImgUrl();
        this.price = dishEntity.getPrice();
        this.portionSize = dishEntity.getPortionSize();
        this.preparationTime = dishEntity.getPreparationTime();
        this.dishCategory = dishEntity.getCategory();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public DishCategory getCategory() {
        return dishCategory;
    }

    public void setCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }
}
