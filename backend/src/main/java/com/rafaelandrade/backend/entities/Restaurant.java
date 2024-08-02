package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.common.OperatingHours;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_restaurant")
public class Restaurant extends LegalEntity{

    private String imgBackgroundUrl;
    @Column(precision = 5, scale = 2)
    private BigDecimal averagePrice;
    private Integer estimatedDeliveryTime;
    private Boolean isOpen;
    @Column(precision = 5, scale = 2)
    private BigDecimal fixedDeliveryFee;

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

    @OneToMany(mappedBy = "restaurant")
    private Set<Order> orderHistory = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<Assessment> assessments = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<AssessmentResponse> assessmentResponses = new HashSet<>();

    /*
    private Double averageRating;
    private Integer numberOfReviews;
     */

    public Restaurant(){
    }

    public Restaurant(String imgBackgroundUrl, BigDecimal averagePrice, Integer estimatedDeliveryTime, Boolean isOpen, BigDecimal fixedDeliveryFee) {
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.isOpen = isOpen;
        this.fixedDeliveryFee = fixedDeliveryFee;
    }

    public Restaurant(String taxIdentificationNumber, String companyName, Address address, String imgBackgroundUrl, BigDecimal averagePrice, Integer estimatedDeliveryTime, Boolean isOpen, BigDecimal fixedDeliveryFee) {
        super(taxIdentificationNumber, companyName, address);
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.isOpen = isOpen;
        this.fixedDeliveryFee = fixedDeliveryFee;
    }

    public Restaurant(Long id, String email, String password, String dateOfBirth, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active, String taxIdentificationNumber, String companyName, Address address, String imgBackgroundUrl, BigDecimal averagePrice, Integer estimatedDeliveryTime, Boolean isOpen, BigDecimal fixedDeliveryFee) {
        super(id, email, password, dateOfBirth, phoneNumber, profilePictureUrl, biography, createdAt, updatedAt, active, taxIdentificationNumber, companyName, address);
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.isOpen = isOpen;
        this.fixedDeliveryFee = fixedDeliveryFee;
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

    public BigDecimal getFixedDeliveryFee() {
        return fixedDeliveryFee;
    }

    public void setFixedDeliveryFee(BigDecimal fixedDeliveryFee) {
        this.fixedDeliveryFee = fixedDeliveryFee;
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

    public Set<Order> getOrderHistory() {
        return orderHistory;
    }

    public Set<Assessment> getAssessments() {
        return assessments;
    }

    public Set<AssessmentResponse> getAssessmentResponses() {
        return assessmentResponses;
    }
}
