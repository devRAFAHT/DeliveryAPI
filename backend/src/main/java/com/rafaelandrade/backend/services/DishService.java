package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.*;
import com.rafaelandrade.backend.entities.Additional;
import com.rafaelandrade.backend.entities.DishCategory;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.DishVariation;
import com.rafaelandrade.backend.repositories.AdditionalRepository;
import com.rafaelandrade.backend.repositories.DishCategoryRepository;
import com.rafaelandrade.backend.repositories.DishRepository;
import com.rafaelandrade.backend.repositories.DishVariationRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelandrade.backend.services.util.CalculateDiscount;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DishService.class);

    private final DishRepository dishRepository;
    private final DishCategoryRepository dishCategoryRepository;
    private final DishVariationRepository dishVariationRepository;
    private final AdditionalRepository additionalRepository;

    @Autowired
    public DishService(DishRepository dishRepository, DishCategoryRepository dishCategoryRepository,
                       DishVariationRepository dishVariationRepository, AdditionalRepository additionalRepository) {
        this.dishRepository = dishRepository;
        this.dishCategoryRepository = dishCategoryRepository;
        this.dishVariationRepository = dishVariationRepository;
        this.additionalRepository = additionalRepository;
    }

    @Transactional(readOnly = true)
    public Page<DishDTO> findAll(Pageable pageable) {
        logger.info("Finding all dishes with pageable: {}", pageable);
        Page<Dish> dishes = dishRepository.findAll(pageable);
        return dishes.map(dish -> new DishDTO(dish, dish.getAdditional(), dish.getVariations()));
    }

    @Transactional(readOnly = true)
    public DishDTO findByName(String name) throws ResourceNotFoundException {
        logger.info("Finding dish by name: {}", name);
        Optional<Dish> dishObj = dishRepository.findByName(name);
        Dish dishEntity = dishObj.orElseThrow(() -> {
            logger.error("Dish with name {} not found", name);
            return new ResourceNotFoundException("Dish not found");
        });
        return new DishDTO(dishEntity, dishEntity.getAdditional(), dishEntity.getVariations());
    }

    @Transactional(readOnly = true)
    public DishDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding dish by id: {}", id);
        Optional<Dish> dishObj = dishRepository.findById(id);
        Dish dishEntity = dishObj.orElseThrow(() -> {
            logger.error("Dish with id {} not found", id);
            return new ResourceNotFoundException("Dish not found");
        });
        return new DishDTO(dishEntity, dishEntity.getAdditional(), dishEntity.getVariations());
    }

    @Transactional
    public DishDTO insert(DishDTO dishDTO) throws ResourceNotFoundException {
        logger.info("Inserting dish: {}", dishDTO);
        Dish dishEntity = new Dish();
        checksIfAssociatedEntitiesExist(dishDTO);
        createDishVariation(dishEntity, dishDTO);
        copyDtoToEntity(dishDTO, dishEntity);
        calculateDiscount(dishEntity);
        dishEntity = dishRepository.save(dishEntity);
        return new DishDTO(dishEntity, dishEntity.getAdditional(), dishEntity.getVariations());
    }

    @Transactional
    public DishDTO update(Long id, DishDTO dishDTO) throws ResourceNotFoundException {
        logger.info("Updating dish with id: {}", id);
        try {
            Dish dishEntity = dishRepository.getReferenceById(id);
            checksIfAssociatedEntitiesExist(dishDTO);
            createDishVariation(dishEntity, dishDTO);
            copyDtoToEntity(dishDTO, dishEntity);
            calculateDiscount(dishEntity);
            dishEntity = dishRepository.save(dishEntity);
            return new DishDTO(dishEntity, dishEntity.getAdditional(), dishEntity.getVariations());
        } catch (EntityNotFoundException e) {
            logger.error("Dish with id {} not found", id, e);
            throw new ResourceNotFoundException("Dish not found with id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting dish with id: {}", id);
        if (!dishRepository.existsById(id)) {
            logger.error("Dish with id {} not found", id);
            throw new ResourceNotFoundException("Dish not found with id: " + id);
        }

        try {
            dishRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting dish with id: {}", id, e);
            throw new DatabaseException("Integrity violation while deleting dish");
        }
    }

    private void copyDtoToEntity(DishDTO dishDTO, Dish dishEntity) throws ResourceNotFoundException {
        logger.debug("Copying DTO to entity: {}", dishDTO);
        dishEntity.setName(dishDTO.getName());
        dishEntity.setDescription(dishDTO.getDescription());
        dishEntity.setImgUrl(dishDTO.getImgUrl());
        dishEntity.setPreparationTime(dishDTO.getPreparationTime());
        dishEntity.setFoodRestriction(dishDTO.getFoodRestriction());
        dishEntity.setSaleStatus(dishDTO.getSaleStatus());
        dishEntity.setOriginalPrice(dishDTO.getOriginalPrice());
        dishEntity.setCurrentPrice(dishDTO.getCurrentPrice());
        dishEntity.setDiscountInPercentage(dishDTO.getDiscountInPercentage());
        dishEntity.setCategory(new DishCategory(dishDTO.getDishCategory()));

        dishEntity.getAdditional().clear();
        for (AdditionalDTO additionalDTO : dishDTO.getAdditional()) {
            Additional additional = additionalRepository.getReferenceById(additionalDTO.getId());
            dishEntity.getAdditional().add(additional);
        }
    }

    private void calculateDiscount(Dish dish) {
        logger.debug("Calculating discount for dish: {}", dish);
        if (dish.getDiscountInPercentage() == null && dish.getCurrentPrice() == null) {
            dish.setCurrentPrice(dish.getOriginalPrice());
            dish.setDiscountInPercentage(BigDecimal.ZERO);
        } else if (dish.getDiscountInPercentage() == null) {
            dish.setDiscountInPercentage(CalculateDiscount.calculateDiscountInPercentage(dish.getOriginalPrice(), dish.getCurrentPrice()));
        } else {
            dish.setCurrentPrice(CalculateDiscount.calculateDiscountInMoney(dish.getOriginalPrice(), dish.getDiscountInPercentage()));
        }
    }

    private void checksIfAssociatedEntitiesExist(DishDTO dishDTO) throws ResourceNotFoundException {
        logger.debug("Checking if associated entities exist for dishDTO: {}", dishDTO);
        long categoryId = dishDTO.getDishCategory().getId();

        if (!dishCategoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Dish category with id " + categoryId + " not found.");
        }

        for (AdditionalDTO additionalDTO : dishDTO.getAdditional()) {
            long additionalId = additionalDTO.getId();
            if (!additionalRepository.existsById(additionalId)) {
                throw new ResourceNotFoundException("Additional with id " + additionalId + " not found.");
            }
        }
    }

    private void createDishVariation(Dish dishEntity, DishDTO dishDTO) {
        logger.debug("Creating dish variations for dishDTO: {}", dishDTO);
        dishEntity.getVariations().clear();
        for (DishVariationDTO dishVariationDTO : dishDTO.getVariations()) {
            logger.debug("Processing dishVariationDTO: {}", dishVariationDTO);
            DishVariation dishVariation = new DishVariation(dishVariationDTO);
            dishVariation.setDish(dishEntity);
            dishVariationRepository.save(dishVariation);
        }
    }
}