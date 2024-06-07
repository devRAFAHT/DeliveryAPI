package com.rafaelandrade.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_additional_category")
public class AdditionalCategory extends Category{

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Additional> additional = new HashSet<>();

    public Set<Additional> getAdditional() {
        return additional;
    }
}
