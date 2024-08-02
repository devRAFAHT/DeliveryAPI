package com.rafaelandrade.backend.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;

import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
public class LegalEntity extends Person{

    private String taxIdentificationNumber;
    private String companyName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    public LegalEntity(){
    }

    public LegalEntity(String taxIdentificationNumber, String companyName, Address address) {
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.companyName = companyName;
        this.address = address;
    }

    public LegalEntity(Long id, String email, String password, String dateOfBirth, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active, String taxIdentificationNumber, String companyName, Address address) {
        super(id, email, password, dateOfBirth, phoneNumber, profilePictureUrl, biography, createdAt, updatedAt, active);
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.companyName = companyName;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
