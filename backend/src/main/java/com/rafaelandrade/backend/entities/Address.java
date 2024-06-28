package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.common.ResidenceType;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_address")
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String neighborhood;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private Integer residenceType;
    @Column(nullable = false)
    private Integer residenceNumber;
    private String floor;
    private String apartmentNumber;
    private String complement;

    @OneToOne(mappedBy = "address")
    private Restaurant restaurant;

    public Address(){
    }

    public Address(Long id, String country, String postalCode, String state, String city, String neighborhood, String street, ResidenceType residenceType, Integer residenceNumber, String floor, String apartmentNumber, String complement) {
        this.id = id;
        this.country = country;
        this.postalCode = postalCode;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        setResidenceType(residenceType);
        this.residenceNumber = residenceNumber;
        this.floor = floor;
        this.apartmentNumber = apartmentNumber;
        this.complement = complement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public ResidenceType getResidenceType() {
        return ResidenceType.valueOf(residenceType);
    }

    public void setResidenceType(ResidenceType residenceType) {
        if(residenceType != null) {
            this.residenceType = residenceType.getCode();
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getResidenceNumber() {
        return residenceNumber;
    }

    public void setResidenceNumber(Integer houseNumber) {
        this.residenceNumber = houseNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(country, address.country) && Objects.equals(postalCode, address.postalCode) && Objects.equals(state, address.state) && Objects.equals(city, address.city) && Objects.equals(neighborhood, address.neighborhood) && Objects.equals(street, address.street) && residenceType == address.residenceType && Objects.equals(residenceNumber, address.residenceNumber) && Objects.equals(floor, address.floor) && Objects.equals(apartmentNumber, address.apartmentNumber) && Objects.equals(complement, address.complement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, postalCode, state, city, neighborhood, street, residenceType, residenceNumber, floor, apartmentNumber, complement);
    }
}
