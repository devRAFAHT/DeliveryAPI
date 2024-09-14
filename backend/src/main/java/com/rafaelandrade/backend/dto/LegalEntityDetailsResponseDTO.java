package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.LegalEntity;

import java.math.BigDecimal;

public class LegalEntityDetailsResponseDTO {

    private Long id;
    private String companyName;
    private String imgBackgroundUrl;
    private BigDecimal averagePrice;
    private Boolean isOpen;
    private Integer estimatedDeliveryTime;
    private AddressDTO addressDTO;
    private Integer numberOfReviews;
    private BigDecimal averageRating;

    public LegalEntityDetailsResponseDTO(){
    }

    public LegalEntityDetailsResponseDTO(Long id, String companyName, String imgBackgroundUrl, BigDecimal averagePrice, Boolean isOpen, Integer estimatedDeliveryTime, AddressDTO addressDTO, Integer numberOfReviews, BigDecimal averageRating) {
        this.id = id;
        this.companyName = companyName;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.isOpen = isOpen;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.addressDTO = addressDTO;
        this.numberOfReviews = numberOfReviews;
        this.averageRating = averageRating;
    }

    public LegalEntityDetailsResponseDTO(LegalEntity entity) {
        this.id = entity.getId();
        this.companyName = entity.getCompanyName();
        this.imgBackgroundUrl = entity.getImgBackgroundUrl();
        this.averagePrice = entity.getAveragePrice();
        this.isOpen = entity.getOpen();
        this.estimatedDeliveryTime = entity.getEstimatedDeliveryTime();
        this.addressDTO = new AddressDTO(entity.getAddress());
        this.numberOfReviews = entity.getNumberOfReviews();
        this.averageRating = entity.getAverageRating();
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

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Integer getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(Integer estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
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
}
