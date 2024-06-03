package com.rafaelandrade.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_dish_category")
public class DishCategory extends Category{

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    Set<Dish> dishes = new HashSet<>();

    public Set<Dish> getDishes() {
        return dishes;
    }
}
