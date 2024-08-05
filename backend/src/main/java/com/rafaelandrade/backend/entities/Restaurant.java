package com.rafaelandrade.backend.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_restaurant")
public class Restaurant extends LegalEntity{
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JoinTable(name = "tb_association_restaurant_category", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<RestaurantCategory> categories = new HashSet<>();

    public Restaurant(){
    }

    public Set<RestaurantCategory> getCategories() {
        return categories;
    }
}
