package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.Item;
import com.rafaelandrade.backend.entities.common.SaleStatus;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.Menu;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MenuDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String category;
    private SaleStatus saleStatus;

    private List<ItemDTO> items = new ArrayList<>();

    public MenuDTO(){
    }

    public MenuDTO(Long id, String category, SaleStatus saleStatus) {
        this.id = id;
        this.category = category;
        this.saleStatus = saleStatus;
    }

    public MenuDTO(Menu menuEntity) {
        this.id = menuEntity.getId();
        this.category = menuEntity.getCategory();
        this.saleStatus = menuEntity.getSaleStatus();
    }

    public MenuDTO(Menu menuEntity, Set<Item> items){
        this(menuEntity);
        items.forEach(item -> this.items.add(new ItemDTO(item)));
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
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public List<ItemDTO> getItems() {
        return items;
    }
}
