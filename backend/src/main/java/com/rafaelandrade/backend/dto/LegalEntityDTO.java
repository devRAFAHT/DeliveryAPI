package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.entities.common.OperatingHours;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LegalEntityDTO {

    private Long id;
    private String taxIdentificationNumber;
    private String companyName;
    private String imgBackgroundUrl;
    private BigDecimal averagePrice;
    private Boolean isOpen;
    private Integer estimatedDeliveryTime;
    private BigDecimal fixedDeliveryFee;
    private AddressDTO addressDTO;
    private Integer numberOfReviews;
    private BigDecimal averageRating;
    private List<MenuDTO> menus = new ArrayList<>();
    private List<OrderDTO> orderHistory = new ArrayList<>();
    private List<AssessmentDTO> assessments = new ArrayList<>();
    private List<AssessmentResponseDTO> assessmentResponses = new ArrayList<>();
    private List<OperatingHours> operatingHours = new ArrayList<>();

    public LegalEntityDTO(){
    }

    public LegalEntityDTO(String taxIdentificationNumber, String companyName, String imgBackgroundUrl, BigDecimal averagePrice, Boolean isOpen, Integer estimatedDeliveryTime, BigDecimal fixedDeliveryFee, AddressDTO addressDTO) {
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.companyName = companyName;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.isOpen = isOpen;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.fixedDeliveryFee = fixedDeliveryFee;
        this.addressDTO = addressDTO;
    }

    public LegalEntityDTO(LegalEntity legalEntity) {
        this.id = legalEntity.getId();
        this.taxIdentificationNumber = legalEntity.getTaxIdentificationNumber();
        this.companyName = legalEntity.getCompanyName();
        this.imgBackgroundUrl = legalEntity.getImgBackgroundUrl();
        this.averagePrice = legalEntity.getAveragePrice();
        this.isOpen = legalEntity.getOpen();
        this.estimatedDeliveryTime = legalEntity.getEstimatedDeliveryTime();
        this.fixedDeliveryFee = legalEntity.getFixedDeliveryFee();
        this.addressDTO = new AddressDTO(legalEntity.getAddress());
        this.numberOfReviews = legalEntity.getNumberOfReviews();
        this.averagePrice = legalEntity.getAveragePrice();
    }

    public LegalEntityDTO(LegalEntity legalEntity, Set<Menu> menus, Set<Order> orders, Set<Assessment> assessments, Set<AssessmentResponse> assessmentResponses, List<OperatingHours> operatingHours) {
        this(legalEntity);
        menus.forEach(menu -> this.getMenus().add(new MenuDTO(menu)));
        orders.forEach(order -> this.getOrderHistory().add(new OrderDTO(order)));
        assessments.forEach(assessment -> this.getAssessments().add(new AssessmentDTO(assessment)));
        assessmentResponses.forEach(response -> this.getAssessmentResponses().add(new AssessmentResponseDTO(response)));
        this.getOperatingHours().addAll(operatingHours);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
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

    public BigDecimal getFixedDeliveryFee() {
        return fixedDeliveryFee;
    }

    public void setFixedDeliveryFee(BigDecimal fixedDeliveryFee) {
        this.fixedDeliveryFee = fixedDeliveryFee;
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

    public List<MenuDTO> getMenus() {
        return menus;
    }

    public List<OrderDTO> getOrderHistory() {
        return orderHistory;
    }

    public List<AssessmentDTO> getAssessments() {
        return assessments;
    }

    public List<AssessmentResponseDTO> getAssessmentResponses() {
        return assessmentResponses;
    }

    public List<OperatingHours> getOperatingHours() {
        return operatingHours;
    }
}
