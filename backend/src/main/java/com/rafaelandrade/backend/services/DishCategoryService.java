package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DishCategoryDTO;
import com.rafaelandrade.backend.entities.DishCategory;
import com.rafaelandrade.backend.repositories.DishCategoryRepository;
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
public class DishCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(DishCategoryService.class);

    private final DishCategoryRepository dishCategoryRepository;

    public DishCategoryService(DishCategoryRepository dishCategoryRepository) {
        this.dishCategoryRepository = dishCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<DishCategoryDTO> findAll(Pageable pageable) {
        logger.info("Finding all dish categories with pageable: {}", pageable);
        Page<DishCategory> categories = dishCategoryRepository.findAll(pageable);
        return categories.map(category -> new DishCategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public DishCategoryDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding dish category by id: {}", id);
        Optional<DishCategory> categoryObj = dishCategoryRepository.findById(id);
        DishCategory dishCategory = categoryObj.orElseThrow(() -> {
            logger.error("Dish category with id {} not found", id);
            return new ResourceNotFoundException("Entity not found");
        });
        return new DishCategoryDTO(dishCategory);
    }

    @Transactional(readOnly = true)
    public DishCategoryDTO findByName(String name) throws ResourceNotFoundException {
        logger.info("Finding dish category by name: {}", name);
        Optional<DishCategory> categoryObj = dishCategoryRepository.findByName(name);
        DishCategory dishCategoryEntity = categoryObj.orElseThrow(() -> {
            logger.error("Dish category with name {} not found", name);
            return new ResourceNotFoundException("Entity not found");
        });
        return new DishCategoryDTO(dishCategoryEntity);
    }

    @Transactional
    public DishCategoryDTO insert(DishCategoryDTO dishCategoryDTO) {
        logger.info("Inserting dish category: {}", dishCategoryDTO);
        DishCategory dishCategoryEntity = new DishCategory();
        dishCategoryEntity.setName(dishCategoryDTO.getName());
        dishCategoryEntity = dishCategoryRepository.save(dishCategoryEntity);
        return new DishCategoryDTO(dishCategoryEntity);
    }

    @Transactional
    public DishCategoryDTO update(Long id, DishCategoryDTO dishCategoryDTO) throws ResourceNotFoundException {
        logger.info("Updating dish category with id: {}", id);
        try {
            DishCategory dishCategoryEntity = dishCategoryRepository.getReferenceById(id);
            dishCategoryEntity.setName(dishCategoryDTO.getName());
            dishCategoryEntity = dishCategoryRepository.save(dishCategoryEntity);
            return new DishCategoryDTO(dishCategoryEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Id not found: {}", id, e);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting dish category with id: {}", id);
        if (!dishCategoryRepository.existsById(id)) {
            logger.error("Id not found: {}", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            dishCategoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting id: {}", id, e);
            throw new DatabaseException("Integrity violation");
        }
    }
}