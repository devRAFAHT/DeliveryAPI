package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.SaleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_additional")
public class Additional implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String imgUrl;
    @Column(nullable = false)
    private BigDecimal price;
    @Size(max = 512, message = "A descrição deve ter no máximo 512 caracteres")
    private String description;
    @Column(nullable = false)
    private SaleStatus saleStatus;
    @ManyToOne
    @JoinColumn(name = "additional_category_id")
    private AdditionalCategory category;

    @ManyToMany(mappedBy = "additional")
    private Set<Dish> dishes = new HashSet<>();

    public Additional(){
    }

    public Additional(Long id, String name, String imgUrl, BigDecimal price, String description, SaleStatus saleStatus, AdditionalCategory category) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Set<Dish> getDishes() {
        return dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Additional that = (Additional) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
