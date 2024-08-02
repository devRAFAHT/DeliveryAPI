package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.OperatingHours;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MappedSuperclass
public class LegalEntity extends Person{

    private String taxIdentificationNumber;
    private String companyName;
    private String imgBackgroundUrl;
    @Column(precision = 5, scale = 2)
    private BigDecimal averagePrice;
    private Boolean isOpen;
    private Integer estimatedDeliveryTime;
    @Column(precision = 5, scale = 2)
    private BigDecimal fixedDeliveryFee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @ElementCollection
    @CollectionTable(name = "tb_legal_entity_operating_hours", joinColumns = @JoinColumn(name = "legal_entity_id"))
    private List<OperatingHours> operatingHours = new ArrayList<>();

    public LegalEntity(){
    }

    public LegalEntity(String taxIdentificationNumber, String companyName, String imgBackgroundUrl, BigDecimal averagePrice, Boolean isOpen, Integer estimatedDeliveryTime, BigDecimal fixedDeliveryFee, Address address) {
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.companyName = companyName;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.isOpen = isOpen;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.fixedDeliveryFee = fixedDeliveryFee;
        this.address = address;
    }

    public LegalEntity(Long id, String email, String password, String dateOfBirth, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active, String taxIdentificationNumber, String companyName, String imgBackgroundUrl, BigDecimal averagePrice, Boolean isOpen, Integer estimatedDeliveryTime, BigDecimal fixedDeliveryFee, Address address) {
        super(id, email, password, dateOfBirth, phoneNumber, profilePictureUrl, biography, createdAt, updatedAt, active);
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.companyName = companyName;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.isOpen = isOpen;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.fixedDeliveryFee = fixedDeliveryFee;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public List<OperatingHours> getOperatingHours() {
        return operatingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegalEntity that = (LegalEntity) o;
        return Objects.equals(taxIdentificationNumber, that.taxIdentificationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taxIdentificationNumber);
    }
}
