package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.common.SaleStatus;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MenuDTO {

    private Long id;
    private String category;
    private SaleStatus saleStatus;

    private List<DishDTO> dishes = new ArrayList<>();
    private List<DrinkDTO> drinks = new ArrayList<>();

    public MenuDTO(){
    }

    public MenuDTO(Long id, String category, SaleStatus saleStatus) {
        this.id = id;
        this.category = category;
        this.saleStatus = saleStatus;
    }

    public MenuDTO(Menu menuEntity) {
        this.id = menuEntity.getId();
        this.category = menuEntity.getCategory();
        this.saleStatus = menuEntity.getSaleStatus();
    }

    public MenuDTO(Menu menuEntity, Set<Dish> dishes, Set<Drink> drinks){
        this(menuEntity);
        dishes.forEach(dish -> this.dishes.add(new DishDTO(dish)));
        drinks.forEach(drink -> this.drinks.add(new DrinkDTO(drink)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public List<DishDTO> getDishes() {
        return dishes;
    }

    public List<DrinkDTO> getDrinks() {
        return drinks;
    }
}
