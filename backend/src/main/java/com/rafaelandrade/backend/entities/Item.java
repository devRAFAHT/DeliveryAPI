package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.SaleStatus;
import com.rafaelandrade.backend.entities.common.UnitMeasurement;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private BigDecimal discountInPercentage;
    private Integer size;
    private Integer unitMeasurement;
    private Integer saleStatus;
    private Duration preparationTime;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToMany(mappedBy = "favoritesItems")
    private Set<User> favoriteBy = new HashSet<>();

    @ManyToMany(mappedBy = "items")
    private Set<Bag> bags = new HashSet<>();

    @ManyToMany(mappedBy = "items")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<Assessment> assessments = new HashSet<>();

    public Item(){
    }

    public Item(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, BigDecimal discountInPercentage, Integer size, UnitMeasurement unitMeasurement, SaleStatus saleStatus, Menu menu) {
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
        this.menu = menu;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Duration getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Duration preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Set<User> getFavoriteBy() {
        return favoriteBy;
    }

    public Set<Bag> getBags() {
        return bags;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Set<Assessment> getAssessments() {
        return assessments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
