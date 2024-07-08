package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.common.OperatingHours;
import com.rafaelandrade.backend.entities.Menu;
import com.rafaelandrade.backend.entities.Restaurant;
import com.rafaelandrade.backend.entities.RestaurantCategory;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RestaurantDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
    private String phoneNumber;
    private String imgProfileUrl;
    private String imgBackgroundUrl;
    private BigDecimal averagePrice;
    private Integer estimatedDeliveryTime;
    private Boolean isOpen;
    private AddressDTO address;
    private List<MenuDTO> menus = new ArrayList<>();
    private List<RestaurantCategoryDTO> categories = new ArrayList<>();
    private List<OperatingHours> operatingHours = new ArrayList<>();

    public RestaurantDTO(){
    }

    public RestaurantDTO(Long id, String name, String description, Instant createdAt, String phoneNumber, String imgProfileUrl, String imgBackgroundUrl, BigDecimal averagePrice, Integer estimatedDeliveryTime, AddressDTO address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        this.imgProfileUrl = imgProfileUrl;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.address = address;
    }

    public RestaurantDTO(Restaurant restaurantEntity) {
        this.id = restaurantEntity.getId();
        this.name = restaurantEntity.getName();
        this.description = restaurantEntity.getDescription();
        this.createdAt = restaurantEntity.getCreatedAt();
        this.phoneNumber = restaurantEntity.getPhoneNumber();
        this.imgProfileUrl = restaurantEntity.getImgProfileUrl();
        this.imgBackgroundUrl = restaurantEntity.getImgBackgroundUrl();
        this.averagePrice = restaurantEntity.getAveragePrice();
        this.estimatedDeliveryTime = restaurantEntity.getEstimatedDeliveryTime();
        this.address = new AddressDTO(restaurantEntity.getAddress());
    }

    public RestaurantDTO(Restaurant restaurantEntity, Set<Menu> menus, Set<RestaurantCategory> categories, List<OperatingHours> operatingHours) {
        this(restaurantEntity);
        menus.forEach(menu -> this.menus.add(new MenuDTO(menu)));
        categories.forEach(category -> this.categories.add(new RestaurantCategoryDTO(category)));
        this.operatingHours.addAll(operatingHours);
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<MenuDTO> getMenus() {
        return menus;
    }

    public List<RestaurantCategoryDTO> getCategories() {
        return categories;
    }

    public List<OperatingHours> getOperatingHours() {
        return operatingHours;
    }
}