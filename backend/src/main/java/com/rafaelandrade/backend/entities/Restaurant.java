package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.common.OperatingHours;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "tb_restaurant")
public class Restaurant implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Instant createdAt;
    @Column(nullable = false)
    private String phoneNumber;
    private String imgProfileUrl;
    private String imgBackgroundUrl;
    @Column(precision = 5, scale = 2)
    private BigDecimal averagePrice;
    private Integer estimatedDeliveryTime;
    private Boolean isOpen;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Menu> menus = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_association_restaurant_category", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<RestaurantCategory> categories = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "tb_restaurant_operating_hours", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<OperatingHours> operatingHours = new ArrayList<>();

    @ManyToMany(mappedBy = "favoritesRestaurants")
    private Set<User> favoriteBy = new HashSet<>();

    /*private Set<Assessment> assessments;
    private Double averageRating;
    private Integer numberOfReviews;
     */

    public Restaurant(){
    }

    public Restaurant(Long id, String name, String description, Instant createdAt, String phoneNumber, String imgProfileUrl, String imgBackgroundUrl, BigDecimal averagePrice, Integer estimatedDeliveryTime, Boolean isOpen ,Address address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        this.imgProfileUrl = imgProfileUrl;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.isOpen = isOpen;
        this.address = address;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImgProfileUrl() {
        return imgProfileUrl;
    }

    public void setImgProfileUrl(String imgProfileUrl) {
        this.imgProfileUrl = imgProfileUrl;
    }

    public String getImgBackgroundUrl() {
        return imgBackgroundUrl;
    }

    public void setImgBackgroundUrl(String imgBackgroundUrl) {
        this.imgBackgroundUrl = imgBackgroundUrl;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Integer getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(Integer estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Set<RestaurantCategory> getCategories() {
        return categories;
    }

    public List<OperatingHours> getOperatingHours() {
        return operatingHours;
    }

    public Set<User> getFavoriteBy() {
        return favoriteBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
