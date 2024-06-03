package com.rafaelandrade.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_drink_category")
public class DrinkCategory extends Category{

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    Set<Drink> drinks = new HashSet<>();

    public Set<Drink> getDrinks() {
        return drinks;
    }
}
