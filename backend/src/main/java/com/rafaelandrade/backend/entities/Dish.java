package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.common.FoodRestriction;
import com.rafaelandrade.backend.common.PortionSize;
import com.rafaelandrade.backend.common.SaleStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private String imgUrl;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal originalPrice;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal currentPrice;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountInPercentage;
    @Column(nullable = false)
    private Integer portionSize;
    private Duration preparationTime;
    @Column(nullable = false)
    private Integer foodRestriction;
    @Column(nullable = false)
    private Integer saleStatus;

    @ManyToOne
    @JoinColumn(name = "dish_category_id")
    private DishCategory category;

    @ManyToMany()
    @JoinTable(name = "tb_dish_additional", joinColumns = @JoinColumn(name = "dish_id"), inverseJoinColumns = @JoinColumn(name = "additional_id"))
    private Set<Additional> additional = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToMany(mappedBy = "favoritesDishes")
    private Set<User> favoriteBy = new HashSet<>();

    @ManyToMany(mappedBy = "dishes")
    private Set<Bag> bags = new HashSet<>();

    @ManyToMany(mappedBy = "dishes")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "dish")
    private Set<Assessment> assessments = new HashSet<>();

    public Dish() {
    }

    public Dish(Long id, String name, String description, String imgUrl, BigDecimal originalPrice, BigDecimal currentPrice, PortionSize portionSize, Duration preparationTime, FoodRestriction foodRestriction, SaleStatus saleStatus, DishCategory category, BigDecimal discountInPercentage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        setPortionSize(portionSize);
        this.preparationTime = preparationTime;
        setFoodRestriction(foodRestriction);
        setSaleStatus(saleStatus);
        this.category = category;
        this.discountInPercentage = discountInPercentage;
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

    public FoodRestriction getFoodRestriction() {
        return FoodRestriction.valueOf(foodRestriction);
    }

    public void setFoodRestriction(FoodRestriction foodRestriction) {
        if (foodRestriction != null) {
            this.foodRestriction = foodRestriction.getCode();
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

    public DishCategory getCategory() {
        return category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public Set<Additional> getAdditional() {
        return additional;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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
