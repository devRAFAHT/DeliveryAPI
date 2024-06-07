package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.AdditionalCategory;
public class AdditionalCategoryDTO extends CategoryDTO{

    public AdditionalCategoryDTO(AdditionalCategory additionalCategory) {
        this.setId(additionalCategory.getId());
        this.setName(additionalCategory.getName());
    }

}
