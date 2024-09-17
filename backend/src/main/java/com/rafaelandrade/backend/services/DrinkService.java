package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DrinkDTO;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.DrinkCategory;
import com.rafaelandrade.backend.repositories.DrinkCategoryRepository;
import com.rafaelandrade.backend.repositories.DrinkRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelandrade.backend.services.util.CalculateDiscount;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DrinkService {

    private static final Logger logger = LoggerFactory.getLogger(DrinkService.class);

    private final DrinkRepository drinkRepository;
    private final DrinkCategoryRepository drinkCategoryRepository;

    public DrinkService(DrinkRepository drinkRepository, DrinkCategoryRepository drinkCategoryRepository) {
        this.drinkRepository = drinkRepository;
        this.drinkCategoryRepository = drinkCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<DrinkDTO> findAll(Pageable pageable) {
        logger.info("Fetching all drinks with pageable: {}", pageable);
        Page<Drink> drinks = drinkRepository.findAll(pageable);
        return drinks.map(drink -> new DrinkDTO(drink));
    }

    @Transactional(readOnly = true)
    public DrinkDTO findByName(String name) throws ResourceNotFoundException {
        logger.info("Finding drink by name: {}", name);
        Optional<Drink> drinkObj = drinkRepository.findByName(name);
        Drink drinkEntity = drinkObj.orElseThrow(() -> {
            logger.error("Drink with name '{}' not found.", name);
            return new ResourceNotFoundException("Drink with name '" + name + "' not found.");
        });
        return new DrinkDTO(drinkEntity);
    }

    @Transactional(readOnly = true)
    public DrinkDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding drink by id: {}", id);
        Optional<Drink> drinkObj = drinkRepository.findById(id);
        Drink drinkEntity = drinkObj.orElseThrow(() -> {
            logger.error("Drink with id {} not found.", id);
            return new ResourceNotFoundException("Drink with id " + id + " not found.");
        });
        return new DrinkDTO(drinkEntity);
    }

    @Transactional
    public DrinkDTO insert(DrinkDTO drinkDTO) throws ResourceNotFoundException {
        logger.info("Inserting new drink with DTO: {}", drinkDTO);
        Drink drinkEntity = new Drink();
        checksIfAssociatedEntitiesExist(drinkDTO);
        copyDtoToEntity(drinkDTO, drinkEntity);
        calculateDiscount(drinkEntity);
        drinkEntity = drinkRepository.save(drinkEntity);
        return new DrinkDTO(drinkEntity);
    }

    @Transactional
    public DrinkDTO update(Long id, DrinkDTO drinkDTO) throws ResourceNotFoundException {
        logger.info("Updating drink with id: {} and DTO: {}", id, drinkDTO);
        try {
            Drink drinkEntity = drinkRepository.getReferenceById(id);
            checksIfAssociatedEntitiesExist(drinkDTO);
            copyDtoToEntity(drinkDTO, drinkEntity);
            calculateDiscount(drinkEntity);
            drinkEntity = drinkRepository.save(drinkEntity);
            return new DrinkDTO(drinkEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Id not found: {}", id, e);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting drink with id: {}", id);
        if (!drinkRepository.existsById(id)) {
            logger.error("Id not found: {}", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            drinkRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting drink with id: {}", id, e);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(DrinkDTO drinkDTO, Drink drink) throws ResourceNotFoundException {
        logger.debug("Copying DTO to entity: {}", drinkDTO);
        drink.setName(drinkDTO.getName());
        drink.setDescription(drinkDTO.getDescription());
        drink.setImgUrl(drinkDTO.getImgUrl());
        drink.setOriginalPrice(drinkDTO.getOriginalPrice());
        drink.setCurrentPrice(drinkDTO.getCurrentPrice());
        drink.setDiscountInPercentage(drinkDTO.getDiscountInPercentage());
        drink.setSize(drinkDTO.getSize());
        drink.setUnitMeasurement(drinkDTO.getUnitMeasurement());
        drink.setSaleStatus(drinkDTO.getSaleStatus());
        drink.setCategory(new DrinkCategory(drinkDTO.getCategory()));
    }

    private void calculateDiscount(Drink drink) {
        logger.debug("Calculating discount for drink: {}", drink);
        if (drink.getDiscountInPercentage() == null && drink.getCurrentPrice() == null) {
            drink.setCurrentPrice(drink.getOriginalPrice());
            drink.setDiscountInPercentage(BigDecimal.valueOf(0.00));
        } else if (drink.getDiscountInPercentage() == null) {
            drink.setDiscountInPercentage(CalculateDiscount.calculateDiscountInPercentage(drink.getOriginalPrice(), drink.getCurrentPrice()));
        } else {
            drink.setCurrentPrice(CalculateDiscount.calculateDiscountInMoney(drink.getOriginalPrice(), drink.getDiscountInPercentage()));
        }
    }

    private void checksIfAssociatedEntitiesExist(DrinkDTO drinkDTO) throws ResourceNotFoundException {
        logger.debug("Checking if associated entities exist for drink DTO: {}", drinkDTO);
        Long categoryId = drinkDTO.getCategory().getId();

        if (!drinkCategoryRepository.existsById(categoryId)) {
            logger.error("Drink category with id {} not found.", categoryId);
            throw new ResourceNotFoundException("Drink category with id " + categoryId + " not found.");
        }
    }
}