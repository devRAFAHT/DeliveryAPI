package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.common.SaleStatus;
import com.rafaelandrade.backend.entities.common.UnitMeasurement;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.Menu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class DrinkDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 60, message = "O nome dever ter no máximo 60 caracteres")
    private String name;
    @Size(max = 512, message = "A descrição deve ter no máximo 512 caracteres")
    private String description;
    private String imgUrl;
    @Positive(message = "O preço original deve ser positivo")
    private BigDecimal originalPrice;
    @Positive(message = "O preço atual deve ser positivo")
    private BigDecimal currentPrice;
    @Positive(message = "O desconto em porcentagem deve ser positivo")
    private BigDecimal discountInPercentage;
    private Integer size;
    private UnitMeasurement unitMeasurement;
    private SaleStatus saleStatus;
    private DrinkCategoryDTO category;
    private Menu menu;

    public DrinkDTO(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage, Integer size, UnitMeasurement unitMeasurement, SaleStatus saleStatus, DrinkCategoryDTO category) {
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
        this.category = category;
    }

    public DrinkDTO(Drink drinkEntity) {
        this.id = drinkEntity.getId();
        this.name = drinkEntity.getName();
        this.description = drinkEntity.getDescription();
        this.imgUrl = drinkEntity.getImgUrl();
        this.originalPrice = drinkEntity.getOriginalPrice();
        this.currentPrice = drinkEntity.getCurrentPrice();
        this.discountInPercentage = drinkEntity.getDiscountInPercentage();
        this.size = drinkEntity.getSize();
        this.unitMeasurement = drinkEntity.getUnitMeasurement();
        this.saleStatus = drinkEntity.getSaleStatus();
        this.category = new DrinkCategoryDTO(drinkEntity.getCategory());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Campo obrigatório") @Size(max = 60, message = "O nome dever ter no máximo 60 caracteres") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Campo obrigatório") @Size(max = 60, message = "O nome dever ter no máximo 60 caracteres") String name) {
        this.name = name;
    }

    public @Size(max = 512, message = "A descrição deve ter no máximo 512 caracteres") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 512, message = "A descrição deve ter no máximo 512 caracteres") String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public @Positive(message = "O preço original deve ser positivo") BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(@Positive(message = "O preço original deve ser positivo") BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public @Positive(message = "O preço atual deve ser positivo") BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(@Positive(message = "O preço atual deve ser positivo") BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public @Positive(message = "O desconto em porcentagem deve ser positivo") BigDecimal getDiscountInPercentage() {
        return discountInPercentage;
    }

    public void setDiscountInPercentage(@Positive(message = "O desconto em porcentagem deve ser positivo") BigDecimal discountInPercentage) {
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

    public DrinkCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(DrinkCategoryDTO category) {
        this.category = category;
    }
}
