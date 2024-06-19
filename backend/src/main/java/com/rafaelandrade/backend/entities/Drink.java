package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.common.SaleStatus;
import com.rafaelandrade.backend.common.UnitMeasurement;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_drink")
public class Drink implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String imgUrl;
    @Column(nullable = false)
    private BigDecimal originalPrice;
    @Column(nullable = false)
    private BigDecimal currentPrice;
    @Column(nullable = false)
    private BigDecimal discountInPercentage;
    @Column(nullable = false)
    private Integer size;
    @Column(nullable = false)
    private Integer unitMeasurement;
    @Column(nullable = false)
    private Integer saleStatus;

    @ManyToOne
    @JoinColumn(name = "drink_category_id")
    private DrinkCategory category;

    @ManyToOne()
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Drink(){
    }

    public Drink(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage,Integer size, UnitMeasurement unitMeasurement, SaleStatus saleStatus, DrinkCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.discountInPercentage = discountInPercentage;
        this.size = size;
        setUnitMeasurement(unitMeasurement);
        setSaleStatus(saleStatus);
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

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getDiscountInPercentage() {
        return discountInPercentage;
    }

    public void setDiscountInPercentage(BigDecimal discountInPercentage) {
        this.discountInPercentage = discountInPercentage;
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

    public SaleStatus getSaleStatus() {
        return SaleStatus.valueOf(saleStatus);
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        if (saleStatus != null) {
            this.saleStatus = saleStatus.getCode();
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

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Objects.equals(id, drink.id);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
