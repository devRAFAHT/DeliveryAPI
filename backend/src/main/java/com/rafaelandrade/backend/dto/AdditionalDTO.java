package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.common.SaleStatus;
import com.rafaelandrade.backend.entities.AdditionalCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AdditionalDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 60, message = "O nome dever ter no máximo 60 caracteres")
    private String name;
    private String imgUrl;
    @Positive(message = "O preço deve ser positivo")
    private BigDecimal price;
    private String description;
    private SaleStatus saleStatus;
    private AdditionalCategory category;

    public AdditionalDTO(){
    }

    public AdditionalDTO(Long id, String name, String imgUrl, BigDecimal price, String description, SaleStatus saleStatus, AdditionalCategory category) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.description = description;
        this.saleStatus = saleStatus;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Campo obrigatório") @Size(max = 60, message = "O nome dever ter no máximo 60 caracteres") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Campo obrigatório") @Size(max = 60, message = "O nome dever ter no máximo 60 caracteres") String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public @Positive(message = "O preço deve ser positivo") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@Positive(message = "O preço deve ser positivo") BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public AdditionalCategory getCategory() {
        return category;
    }

    public void setCategory(AdditionalCategory category) {
        this.category = category;
    }
}
