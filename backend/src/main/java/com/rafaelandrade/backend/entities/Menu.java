package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.SaleStatus;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private Integer saleStatus;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private Set<Dish> dishes = new HashSet<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private Set<Drink> drinks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Menu(){
    }

    public Menu(Long id, String category, Integer saleStatus) {
        this.id = id;
        this.category = category;
        this.saleStatus = saleStatus;
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
        return SaleStatus.valueOf(saleStatus);
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        if (saleStatus != null) {
            this.saleStatus = saleStatus.getCode();
        }
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public Set<Drink> getDrinks() {
        return drinks;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
