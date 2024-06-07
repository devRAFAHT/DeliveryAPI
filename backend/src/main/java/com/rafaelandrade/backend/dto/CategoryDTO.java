package com.rafaelandrade.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 60)
    private String name;

    public CategoryDTO(){
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Campo obrigatório") @Size(max = 60) String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Campo obrigatório") @Size(max = 60) String name) {
        this.name = name;
    }
}
