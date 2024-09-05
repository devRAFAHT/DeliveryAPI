package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.DishVariation;
import com.rafaelandrade.backend.entities.common.FoodRestriction;
import com.rafaelandrade.backend.entities.common.SaleStatus;
import com.rafaelandrade.backend.entities.Additional;
import com.rafaelandrade.backend.entities.Dish;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DishDTO implements Serializable {
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
    @PositiveOrZero(message = "O desconto em porcentagem deve ser positivo")
    private BigDecimal discountInPercentage;
    private Duration preparationTime;
    private FoodRestriction foodRestriction;
    private SaleStatus saleStatus;
    private DishCategoryDTO dishCategory;

    List<AdditionalDTO> additional = new ArrayList<AdditionalDTO>();

    List<DishVariationDTO> variations =  new ArrayList<>();

    public DishDTO() {
    }

    public DishDTO(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage, Duration preparationTime, FoodRestriction foodRestriction, SaleStatus saleStatus, DishCategoryDTO dishCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.discountInPercentage = discountInPercentage;
        this.preparationTime = preparationTime;
        this.foodRestriction = foodRestriction;
        this.saleStatus = saleStatus;
        this.dishCategory = dishCategory;
    }

    public DishDTO(Dish dishEntity) {
        this.id = dishEntity.getId();
        this.name = dishEntity.getName();
        this.description = dishEntity.getDescription();
        this.imgUrl = dishEntity.getImgUrl();
        this.originalPrice = dishEntity.getOriginalPrice();
        this.currentPrice = dishEntity.getCurrentPrice();
        this.discountInPercentage = dishEntity.getDiscountInPercentage();
        this.preparationTime = dishEntity.getPreparationTime();
        this.foodRestriction = dishEntity.getFoodRestriction();
        this.saleStatus = dishEntity.getSaleStatus();
        this.dishCategory = new DishCategoryDTO(dishEntity.getCategory());
    }

    public DishDTO(Dish entity, Set<Additional> additional, Set<DishVariation> variations){
        this(entity);
        additional.forEach(adt -> this.additional.add(new AdditionalDTO(adt)));
        variations.forEach(variation -> this.variations.add(new DishVariationDTO(variation)));
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

    public Duration getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Duration preparationTime) {
        this.preparationTime = preparationTime;
    }

    public FoodRestriction getFoodRestriction() {
        return foodRestriction;
    }

    public void setFoodRestriction(FoodRestriction foodRestriction) {
        this.foodRestriction = foodRestriction;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public DishCategoryDTO getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(DishCategoryDTO dishCategory) {
        this.dishCategory = dishCategory;
    }

    public List<AdditionalDTO> getAdditional() {
        return additional;
    }

    public List<DishVariationDTO> getVariations() {
        return variations;
    }
}
