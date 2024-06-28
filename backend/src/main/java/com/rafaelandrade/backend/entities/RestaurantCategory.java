package com.rafaelandrade.backend.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_restaurant_category")
public class RestaurantCategory extends Category{

    @ManyToMany(mappedBy = "categories")
    Set<Restaurant> restaurants = new HashSet<>();

    public  RestaurantCategory(){
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }
}
