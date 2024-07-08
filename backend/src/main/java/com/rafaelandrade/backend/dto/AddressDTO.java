package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.common.ResidenceType;
import com.rafaelandrade.backend.entities.Address;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public class AddressDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String country;
    private String postalCode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private ResidenceType residenceType;
    private Integer residenceNumber;
    private String floor;
    private String apartmentNumber;
    @Size(max = 30, message = "O complemento dever ter no máximo 30 caracteres")
    private String complement;

    public AddressDTO(){
    }

    public AddressDTO(Long id, String country, String postalCode, String state, String city, String neighborhood, String street, ResidenceType residenceType, Integer residenceNumber, String floor, String apartmentNumber, String complement) {
        this.id = id;
        this.country = country;
        this.postalCode = postalCode;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.residenceType = residenceType;
        this.residenceNumber = residenceNumber;
        this.floor = floor;
        this.apartmentNumber = apartmentNumber;
        this.complement = complement;
    }

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.country = address.getCountry();
        this.postalCode = address.getPostalCode();
        this.state = address.getState();
        this.city = address.getCity();
        this.neighborhood = address.getNeighborhood();
        this.street = address.getStreet();
        this.residenceType = address.getResidenceType();
        this.residenceNumber = address.getResidenceNumber();
        this.floor = address.getFloor();
        this.apartmentNumber = address.getApartmentNumber();
        this.complement = address.getComplement();
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public ResidenceType getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(ResidenceType residenceType) {
        this.residenceType = residenceType;
    }

    public Integer getResidenceNumber() {
        return residenceNumber;
    }

    public void setResidenceNumber(Integer residenceNumber) {
        this.residenceNumber = residenceNumber;
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
}
