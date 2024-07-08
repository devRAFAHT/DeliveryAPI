package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.AdditionalCategory;

import java.io.Serial;

public class AdditionalCategoryDTO extends CategoryDTO{
    @Serial
    private static final long serialVersionUID = 1L;

    public AdditionalCategoryDTO(){
    }

    public AdditionalCategoryDTO(AdditionalCategory additionalCategory) {
        this.setId(additionalCategory.getId());
        this.setName(additionalCategory.getName());
    }

}
