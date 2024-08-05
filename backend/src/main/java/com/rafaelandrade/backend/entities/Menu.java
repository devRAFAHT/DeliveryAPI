package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.SaleStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_menu")
public class Menu implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private Integer saleStatus;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;

    public Menu(){
    }

    public Menu(Long id, String category, Integer saleStatus) {
        this.id = id;
        this.category = category;
        this.saleStatus = saleStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SaleStatus getSaleStatus() {
        return SaleStatus.valueOf(saleStatus);
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        if (saleStatus != null) {
            this.saleStatus = saleStatus.getCode();
        }
    }

    public Set<Item> getItems() {
        return items;
    }

    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
