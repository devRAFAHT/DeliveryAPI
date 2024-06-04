package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.common.UnitMeasurement;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_drink")
public class Drink implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String imgUrl;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer size;
    @Column(nullable = false)
    private Integer unitMeasurement;

    @ManyToOne
    @JoinColumn(name = "drink_category_id")
    private DrinkCategory category;

    public Drink(){
    }

    public Drink(Long id, Long name, String description, String imgUrl, BigDecimal price, Integer size, UnitMeasurement unitMeasurement, DrinkCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.size = size;
        setUnitMeasurement(unitMeasurement);
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public UnitMeasurement getUnitMeasurement() {
        return UnitMeasurement.valueOf(unitMeasurement);
    }

    public void setUnitMeasurement(UnitMeasurement unitMeasurement) {
        if (unitMeasurement != null) {
            this.unitMeasurement = unitMeasurement.getCode();
        }
    }

    public DrinkCategory getCategory() {
        return category;
    }

    public void setCategory(DrinkCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setUnitMeasurement(Integer unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Objects.equals(id, drink.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
