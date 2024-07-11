package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DrinkDTO;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.DrinkCategory;
import com.rafaelandrade.backend.repositories.DrinkCategoryRepository;
import com.rafaelandrade.backend.repositories.DrinkRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelandrade.backend.util.CalculateDiscount;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    private final DrinkCategoryRepository drinkCategoryRepository;

    public DrinkService(DrinkRepository drinkRepository, DrinkCategoryRepository drinkCategoryRepository) {
        this.drinkRepository = drinkRepository;
        this.drinkCategoryRepository = drinkCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<DrinkDTO> findAll(Pageable pageable) {
        Page<Drink> drinks = drinkRepository.findAll(pageable); // Mudança aqui
        return drinks.map(drink -> new DrinkDTO(drink)); // Mudança aqui
    }

    @Transactional(readOnly = true)
    public DrinkDTO findByName(String name) throws ResourceNotFoundException {
        Optional<Drink> drinkObj = drinkRepository.findByName(name);
        Drink drinkEntity = drinkObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DrinkDTO(drinkEntity);
    }

    @Transactional(readOnly = true)
    public DrinkDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Drink> drinkObj = drinkRepository.findById(id);
        Drink drinkEntity = drinkObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DrinkDTO(drinkEntity);
    }

    @Transactional
    public DrinkDTO insert(DrinkDTO drinkDTO) throws ResourceNotFoundException {
        Drink drinkEntity = new Drink();
        copyDtoToEntity(drinkDTO, drinkEntity);
        calculateDiscount(drinkEntity);
        drinkEntity = drinkRepository.save(drinkEntity);
        return new DrinkDTO(drinkEntity);
    }

    @Transactional
    public DrinkDTO update(Long id, DrinkDTO drinkDTO) throws ResourceNotFoundException {
        try {
            Drink drinkEntity = drinkRepository.getReferenceById(id);
            copyDtoToEntity(drinkDTO, drinkEntity);
            calculateDiscount(drinkEntity);
            drinkEntity = drinkRepository.save(drinkEntity);
            return new DrinkDTO(drinkEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if (!drinkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            drinkRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(DrinkDTO drinkDTO, Drink drink) throws ResourceNotFoundException {
        drink.setName(drinkDTO.getName());
        drink.setDescription(drinkDTO.getDescription());
        drink.setImgUrl(drinkDTO.getImgUrl());
        drink.setOriginalPrice(drinkDTO.getOriginalPrice());
        drink.setCurrentPrice(drinkDTO.getCurrentPrice());
        drink.setDiscountInPercentage(drinkDTO.getDiscountInPercentage());
        drink.setSize(drinkDTO.getSize());
        drink.setUnitMeasurement(drinkDTO.getUnitMeasurement());
        drink.setSaleStatus(drinkDTO.getSaleStatus());

        Optional<DrinkCategory> categoryObj = drinkCategoryRepository.findById(drinkDTO.getCategory().getId());
        categoryObj.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        drink.setCategory(new DrinkCategory(drinkDTO.getCategory()));
    }

    private void calculateDiscount(Drink drink) {
        if (drink.getDiscountInPercentage() == null && drink.getCurrentPrice() == null) {
            drink.setCurrentPrice(drink.getOriginalPrice());
            drink.setDiscountInPercentage(BigDecimal.valueOf(0.00));
        } else if (drink.getDiscountInPercentage() == null) {
            drink.setDiscountInPercentage(CalculateDiscount.calculateDiscountInPercentage(drink.getOriginalPrice(), drink.getCurrentPrice()));
        } else {
            drink.setCurrentPrice(CalculateDiscount.calculateDiscountInMoney(drink.getOriginalPrice(), drink.getDiscountInPercentage()));
        }
    }
}
