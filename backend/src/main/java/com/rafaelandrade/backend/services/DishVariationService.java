package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DishDTO;
import com.rafaelandrade.backend.dto.DishVariationDTO;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.DishVariation;
import com.rafaelandrade.backend.repositories.DishRepository;
import com.rafaelandrade.backend.repositories.DishVariationRepository;
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
public class DishVariationService {

    private static final Logger logger = LoggerFactory.getLogger(DishVariationService.class);

    private final DishVariationRepository dishVariationRepository;
    private final DishRepository dishRepository;

    public DishVariationService(DishVariationRepository dishVariationRepository, DishRepository dishRepository) {
        this.dishVariationRepository = dishVariationRepository;
        this.dishRepository = dishRepository;
    }

    @Transactional(readOnly = true)
    public Page<DishVariationDTO> findAll(Pageable pageable) {
        logger.debug("Finding all dish variations with pageable: {}", pageable);
        Page<DishVariation> dishVariations = dishVariationRepository.findAll(pageable);
        return dishVariations.map(dishVariation -> new DishVariationDTO(dishVariation));
    }

    @Transactional(readOnly = true)
    public DishVariationDTO findById(Long id) throws ResourceNotFoundException {
        logger.debug("Finding dish variation by id: {}", id);
        Optional<DishVariation> dishVariationObj = dishVariationRepository.findById(id);
        DishVariation dishVariationEntity = dishVariationObj.orElseThrow(() -> {
            logger.error("Dish variation with id {} not found.", id);
            return new ResourceNotFoundException("Dish variation with id " + id + " not found.");
        });
        return new DishVariationDTO(dishVariationEntity);
    }

    @Transactional
    public DishVariationDTO insert(DishVariationDTO dishVariationDTO) throws ResourceNotFoundException {
        logger.debug("Inserting dish variation: {}", dishVariationDTO);
        DishVariation dishVariationEntity = new DishVariation();
        copyDtoToEntity(dishVariationDTO, dishVariationEntity);

        Long dishId = dishVariationDTO.getDishDTO().getId();
        Optional<Dish> dishObj = dishRepository.findById(dishId);
        Dish dish = dishObj.orElseThrow(() -> {
            logger.error("Dish with id {} not found.", dishId);
            return new ResourceNotFoundException("Dish with id " + dishId + " not found.");
        });

        dishVariationEntity.setDish(dish);
        dishVariationEntity = dishVariationRepository.save(dishVariationEntity);
        logger.info("Dish variation inserted with id: {}", dishVariationEntity.getId());
        return new DishVariationDTO(dishVariationEntity);
    }

    @Transactional
    public DishVariationDTO update(Long id, DishVariationDTO dishVariationDTO) throws ResourceNotFoundException {
        logger.debug("Updating dish variation with id: {} and data: {}", id, dishVariationDTO);
        try {
            DishVariation dishVariationEntity = dishVariationRepository.getReferenceById(id);
            copyDtoToEntity(dishVariationDTO, dishVariationEntity);
            dishVariationEntity = dishVariationRepository.save(dishVariationEntity);
            logger.info("Dish variation updated with id: {}", id);
            return new DishVariationDTO(dishVariationEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Dish variation with id {} not found.", id, e);
            throw new ResourceNotFoundException("Dish variation with id " + id + " not found.");
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.debug("Deleting dish variation with id: {}", id);
        if (!dishVariationRepository.existsById(id)) {
            logger.error("Dish variation with id {} not found.", id);
            throw new ResourceNotFoundException("Dish variation with id " + id + " not found.");
        }

        try {
            dishVariationRepository.deleteById(id);
            logger.info("Dish variation deleted with id: {}", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting dish variation with id: {}", id, e);
            throw new DatabaseException("Integrity violation while deleting dish variation with id " + id);
        }
    }

    private void copyDtoToEntity(DishVariationDTO dishVariationDTO, DishVariation dishVariationEntity) {
        logger.debug("Copying DTO to entity: {}", dishVariationDTO);
        dishVariationEntity.setSku(dishVariationDTO.getSku());
        dishVariationEntity.setPortionSize(dishVariationDTO.getPortionSize());
        dishVariationEntity.setAdditionalPrice(dishVariationDTO.getAdditionalPrice());
    }
}