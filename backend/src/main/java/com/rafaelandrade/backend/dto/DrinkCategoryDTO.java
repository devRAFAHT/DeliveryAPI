package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.DishCategory;
import com.rafaelandrade.backend.entities.DrinkCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public class DrinkCategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 60)
    private String name;

    public DrinkCategoryDTO(){
    }

    public DrinkCategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DrinkCategoryDTO(DrinkCategory drinkCategoryEntity) {
        this.id = drinkCategoryEntity.getId();
        this.name = drinkCategoryEntity.getName();
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
}
