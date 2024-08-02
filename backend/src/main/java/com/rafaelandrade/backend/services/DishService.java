package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AdditionalDTO;
import com.rafaelandrade.backend.dto.DishDTO;
import com.rafaelandrade.backend.entities.Additional;
import com.rafaelandrade.backend.entities.DishCategory;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.repositories.AdditionalRepository;
import com.rafaelandrade.backend.repositories.DishCategoryRepository;
import com.rafaelandrade.backend.repositories.DishRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelandrade.backend.services.util.CalculateDiscount;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;

    private final DishCategoryRepository dishCategoryRepository;

    @Autowired private AdditionalRepository additionalRepository;

    public DishService(DishRepository dishRepository, DishCategoryRepository dishCategoryRepository) {
        this.dishRepository = dishRepository;
        this.dishCategoryRepository = dishCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<DishDTO> findAll(Pageable pageable) {
        Page<Dish> dishes = dishRepository.findAll(pageable);
        return dishes.map(dish -> new DishDTO(dish, dish.getAdditional()));
    }

    @Transactional(readOnly = true)
    public DishDTO findByName(String name) throws ResourceNotFoundException {
        Optional<Dish> dishObj = dishRepository.findByName(name);
        Dish dishEntity = dishObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DishDTO(dishEntity, dishEntity.getAdditional());
    }

    @Transactional(readOnly = true)
    public DishDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Dish> dishObj = dishRepository.findById(id);
        Dish dishEntity = dishObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DishDTO(dishEntity, dishEntity.getAdditional());
    }

    @Transactional
    public DishDTO insert(DishDTO dishDTO) throws ResourceNotFoundException {
        Dish dishEntity = new Dish();
        copyDtoToEntity(dishDTO, dishEntity);
        calculateDiscount(dishEntity);
        dishEntity = dishRepository.save(dishEntity);
        return new DishDTO(dishEntity, dishEntity.getAdditional());
    }

    @Transactional
    public DishDTO update(Long id, DishDTO dishDTO) throws ResourceNotFoundException {
        try {
            Dish dishEntity = dishRepository.getReferenceById(id);
            copyDtoToEntity(dishDTO, dishEntity);
            calculateDiscount(dishEntity);
            dishEntity = dishRepository.save(dishEntity);
            return new DishDTO(dishEntity, dishEntity.getAdditional());
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if(!dishRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            dishRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(DishDTO dishDTO, Dish dishEntity) throws ResourceNotFoundException {
        dishEntity.setName(dishDTO.getName());
        dishEntity.setDescription(dishDTO.getDescription());
        dishEntity.setImgUrl(dishDTO.getImgUrl());
        dishEntity.setPortionSize(dishDTO.getPortionSize());
        dishEntity.setPreparationTime(dishDTO.getPreparationTime());
        dishEntity.setFoodRestriction(dishDTO.getFoodRestriction());
        dishEntity.setSaleStatus(dishDTO.getSaleStatus());
        dishEntity.setOriginalPrice(dishDTO.getOriginalPrice());
        dishEntity.setCurrentPrice(dishDTO.getCurrentPrice());
        dishEntity.setDiscountInPercentage(dishDTO.getDiscountInPercentage());

        Optional<DishCategory> categoryObj = dishCategoryRepository.findById(dishDTO.getDishCategory().getId());
        categoryObj.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        dishEntity.setCategory(new DishCategory(dishDTO.getDishCategory()));

        dishEntity.getAdditional().clear();
        for(AdditionalDTO additionalDTO : dishDTO.getAdditional()){
            Optional<Additional> additionalObj = additionalRepository.findById(additionalDTO.getId());
            additionalObj.orElseThrow(() -> new ResourceNotFoundException("Additional with id " + additionalDTO.getId() + " not found."));
            Additional additional = additionalObj.get();
            dishEntity.getAdditional().add(additional);
        }
    }

    private void calculateDiscount(Dish dish) {
        if (dish.getDiscountInPercentage() == null && dish.getCurrentPrice() == null) {
            dish.setCurrentPrice(dish.getOriginalPrice());
            dish.setDiscountInPercentage(BigDecimal.valueOf(0.00));
        }else if(dish.getDiscountInPercentage() == null){
            dish.setDiscountInPercentage(CalculateDiscount.calculateDiscountInPercentage(dish.getOriginalPrice(), dish.getCurrentPrice()));
        }else{
            dish.setCurrentPrice(CalculateDiscount.calculateDiscountInMoney(dish.getOriginalPrice(), dish.getDiscountInPercentage()));
        }
    }
}