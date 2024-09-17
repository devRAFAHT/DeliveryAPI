package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DrinkCategoryDTO;
import com.rafaelandrade.backend.entities.DrinkCategory;
import com.rafaelandrade.backend.repositories.DrinkCategoryRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DrinkCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(DrinkCategoryService.class);

    private final DrinkCategoryRepository drinkCategoryRepository;

    public DrinkCategoryService(DrinkCategoryRepository drinkCategoryRepository) {
        this.drinkCategoryRepository = drinkCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<DrinkCategoryDTO> findAll(Pageable pageable) {
        logger.debug("Finding all drink categories with pageable: {}", pageable);
        Page<DrinkCategory> categories = drinkCategoryRepository.findAll(pageable);
        return categories.map(category -> new DrinkCategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public DrinkCategoryDTO findById(Long id) throws ResourceNotFoundException {
        logger.debug("Finding drink category by id: {}", id);
        Optional<DrinkCategory> categoryObj = drinkCategoryRepository.findById(id);
        DrinkCategory drinkCategory = categoryObj.orElseThrow(() -> {
            logger.error("Drink category with id {} not found.", id);
            return new ResourceNotFoundException("Drink category with id " + id + " not found.");
        });
        return new DrinkCategoryDTO(drinkCategory);
    }

    @Transactional(readOnly = true)
    public DrinkCategoryDTO findByName(String name) throws ResourceNotFoundException {
        logger.debug("Finding drink category by name: {}", name);
        Optional<DrinkCategory> categoryObj = drinkCategoryRepository.findByName(name);
        DrinkCategory drinkCategory = categoryObj.orElseThrow(() -> {
            logger.error("Drink category with name '{}' not found.", name);
            return new ResourceNotFoundException("Drink category with name '" + name + "' not found.");
        });
        return new DrinkCategoryDTO(drinkCategory);
    }

    @Transactional
    public DrinkCategoryDTO insert(DrinkCategoryDTO drinkCategoryDTO) {
        logger.debug("Inserting drink category: {}", drinkCategoryDTO);
        DrinkCategory drinkCategoryEntity = new DrinkCategory();
        drinkCategoryEntity.setName(drinkCategoryDTO.getName());
        drinkCategoryEntity = drinkCategoryRepository.save(drinkCategoryEntity);
        logger.info("Drink category inserted with id: {}", drinkCategoryEntity.getId());
        return new DrinkCategoryDTO(drinkCategoryEntity);
    }

    @Transactional
    public DrinkCategoryDTO update(Long id, DrinkCategoryDTO drinkCategoryDTO) throws ResourceNotFoundException {
        logger.debug("Updating drink category with id: {} and data: {}", id, drinkCategoryDTO);
        try {
            DrinkCategory drinkCategoryEntity = drinkCategoryRepository.getReferenceById(id);
            drinkCategoryEntity.setName(drinkCategoryDTO.getName());
            drinkCategoryEntity = drinkCategoryRepository.save(drinkCategoryEntity);
            logger.info("Drink category updated with id: {}", id);
            return new DrinkCategoryDTO(drinkCategoryEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Drink category with id {} not found during update.", id, e);
            throw new ResourceNotFoundException("Drink category with id " + id + " not found.");
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.debug("Deleting drink category with id: {}", id);
        if (!drinkCategoryRepository.existsById(id)) {
            logger.error("Drink category with id {} not found for deletion.", id);
            throw new ResourceNotFoundException("Drink category with id " + id + " not found.");
        }

        try {
            drinkCategoryRepository.deleteById(id);
            logger.info("Drink category deleted with id: {}", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting drink category with id: {}", id, e);
            throw new DatabaseException("Integrity violation while deleting drink category with id " + id);
        }
    }
}