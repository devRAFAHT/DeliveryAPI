package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.Bag;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.Drink;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BagDTO {

    private Long id;
    private Integer quantityOfItems;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private List<DishDTO> dishes = new ArrayList<>();
    private List<DrinkDTO> drinks = new ArrayList<>();

    public BagDTO(){
    }

    public BagDTO(Long id) {
        this.id = id;
    }

    public BagDTO(Bag bagEntity) {
        this.id = bagEntity.getId();
        this.quantityOfItems = bagEntity.getQuantityOfItems();
        this.totalPrice = bagEntity.getTotalPrice();
        this.discount = bagEntity.getDiscount();
    }

    public BagDTO (Bag bagEntity, Set<Dish> dishes, Set<Drink> drinks){
        this(bagEntity);
        dishes.forEach(dish -> this.getDishes().add(new DishDTO(dish)));
        drinks.forEach(drink -> this.getDrinks().add(new DrinkDTO(drink)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityOfItems() {
        return quantityOfItems;
    }

    public void setQuantityOfItems(Integer quantityOfItems) {
        this.quantityOfItems = quantityOfItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public List<DishDTO> getDishes() {
        return dishes;
    }

    public List<DrinkDTO> getDrinks() {
        return drinks;
    }
}
