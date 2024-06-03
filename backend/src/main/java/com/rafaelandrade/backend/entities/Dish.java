package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.common.PortionSize;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

@Entity
@Table(name = "tb_dish")
public class Dish implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String imgUrl;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal price;
    private Integer portionSize;
    @Column(nullable = false)
    private Duration preparationTime;

    @ManyToOne
    @JoinColumn(name = "dish_category_id")
    private DishCategory category;

    public Dish() {
    }

    public Dish(Long id, String name, String description, String imgUrl, BigDecimal price, PortionSize portionSize, Duration preparationTime, DishCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        setPortionSize(portionSize);
        this.preparationTime = preparationTime;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PortionSize getPortionSize() {
        return PortionSize.valueOf(portionSize);
    }

    public void setPortionSize(PortionSize portionSize) {
        if (portionSize != null) {
            this.portionSize = portionSize.getCode();
        }
    }

    public Duration getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Duration preparationTime) {
        this.preparationTime = preparationTime;
    }

    public DishCategory getCategory() {
        return category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
