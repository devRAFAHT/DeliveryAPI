package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.OperatingHours;
import jakarta.persistence.*;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class LegalEntity extends Person{
    @Serial
    private static final long serialVersionUID = 1L;

    private String taxIdentificationNumber;
    private String companyName;
    private String imgBackgroundUrl;
    @Column(precision = 5, scale = 2)
    private BigDecimal averagePrice;
    private Boolean isOpen;
    private Integer estimatedDeliveryTime;
    @Column(precision = 5, scale = 2)
    private BigDecimal fixedDeliveryFee;
    private Integer numberOfReviews;
    private BigDecimal averageRating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Menu> menus = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteEstablishments")
    private Set<User> favoriteBy = new HashSet<>();

    @OneToMany(mappedBy = "legalEntity")
    private Set<Order> orderHistory = new HashSet<>();

    @OneToMany(mappedBy = "legalEntity")
    private Set<Assessment> assessments = new HashSet<>();

    @OneToMany(mappedBy = "legalEntity")
    private Set<AssessmentResponse> assessmentResponses = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "tb_legal_entity_operating_hours", joinColumns = @JoinColumn(name = "legal_entity_id"))
    private List<OperatingHours> operatingHours = new ArrayList<>();

    public LegalEntity(){
    }

    public LegalEntity(String taxIdentificationNumber, String companyName, String imgBackgroundUrl, BigDecimal averagePrice, Boolean isOpen, Integer estimatedDeliveryTime, BigDecimal fixedDeliveryFee, BigDecimal averageRating, Address address) {
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.companyName = companyName;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.isOpen = isOpen;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.fixedDeliveryFee = fixedDeliveryFee;
        setNumberOfReviews();
        this.averageRating = averageRating;
        this.address = address;
    }

    public LegalEntity(Long id, String email, String password, String dateOfBirth, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active, String taxIdentificationNumber, String companyName, String imgBackgroundUrl, BigDecimal averagePrice, Boolean isOpen, Integer estimatedDeliveryTime, BigDecimal fixedDeliveryFee, Integer numberOfReviews, BigDecimal averageRating, Address address) {
        super(id, email, password, dateOfBirth, phoneNumber, profilePictureUrl, biography, createdAt, updatedAt, active);
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.companyName = companyName;
        this.imgBackgroundUrl = imgBackgroundUrl;
        this.averagePrice = averagePrice;
        this.isOpen = isOpen;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.fixedDeliveryFee = fixedDeliveryFee;
        setNumberOfReviews();
        this.averageRating = averageRating;
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

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews() {
        this.numberOfReviews = getAssessments().size();
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public List<OperatingHours> getOperatingHours() {
        return operatingHours;
    }

    public Set<Menu> getMenus() {
        return menus;
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
