package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.Menu;
import com.rafaelandrade.backend.entities.Restaurant;
import com.rafaelandrade.backend.entities.RestaurantCategory;
import com.rafaelandrade.backend.entities.common.OperatingHours;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RestaurantDetailsResponseDTO {

    private Long id;
    private String companyName;
    private String imgProfileUrl;
    private String imgBackgroundUrl;
    private BigDecimal averagePrice;
    private Integer estimatedDeliveryTime;
    private Boolean isOpen;
    private AddressDTO address;
    private Integer numberOfReviews;
    private BigDecimal averageRating;
    private List<RestaurantCategoryDTO> categories = new ArrayList<>();

    public RestaurantDetailsResponseDTO(){
    }

    public RestaurantDetailsResponseDTO(Long id, String companyName, String imgProfileUrl, String imgBackgroundUrl, BigDecimal averagePrice, Integer estimatedDeliveryTime, Boolean isOpen, AddressDTO address, Integer numberOfReviews, BigDecimal averageRating) {
        this.id = id;
        this.companyName = companyName;
        this.imgProfileUrl = imgProfileUrl;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.isOpen = isOpen;
        this.address = address;
        this.numberOfReviews = numberOfReviews;
        this.averageRating = averageRating;
    }

    public RestaurantDetailsResponseDTO(Restaurant restaurantEntity) {
        this.id = restaurantEntity.getId();
        this.companyName = restaurantEntity.getCompanyName();
        this.imgProfileUrl = restaurantEntity.getProfilePictureUrl();
        this.imgBackgroundUrl = restaurantEntity.getImgBackgroundUrl();
        this.averagePrice = restaurantEntity.getAveragePrice();
        this.estimatedDeliveryTime = restaurantEntity.getEstimatedDeliveryTime();
        this.isOpen = restaurantEntity.getOpen();
        this.address = new AddressDTO(restaurantEntity.getAddress());
        this.numberOfReviews = restaurantEntity.getNumberOfReviews();
        this.averageRating = restaurantEntity.getAverageRating();
    }

    public RestaurantDetailsResponseDTO(Restaurant restaurantEntity, Set<RestaurantCategory> categories) {
        this(restaurantEntity);
        categories.forEach(category -> this.categories.add(new RestaurantCategoryDTO(category)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(Integer numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public List<RestaurantCategoryDTO> getCategories() {
        return categories;
    }
}
