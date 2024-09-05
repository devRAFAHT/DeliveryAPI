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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DishVariationService {

    private final DishVariationRepository dishVariationRepository;
    private final DishRepository dishRepository;

    public DishVariationService(DishVariationRepository dishVariationRepository, DishRepository dishRepository) {
        this.dishVariationRepository = dishVariationRepository;
        this.dishRepository = dishRepository;
    }

    @Transactional(readOnly = true)
    public Page<DishVariationDTO> findAll(Pageable pageable) {
        Page<DishVariation> dishVariations = dishVariationRepository.findAll(pageable);
        return dishVariations.map(dishVariation -> new DishVariationDTO(dishVariation));
    }

    @Transactional(readOnly = true)
    public DishVariationDTO findById(Long id) throws ResourceNotFoundException {
        Optional<DishVariation> dishVariationObj = dishVariationRepository.findById(id);
        DishVariation dishVariationEntity = dishVariationObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DishVariationDTO(dishVariationEntity);
    }

    @Transactional
    public DishVariationDTO insert(DishVariationDTO dishVariationDTO) throws ResourceNotFoundException {
        DishVariation dishVariationEntity = new DishVariation();
        copyDtoToEntity(dishVariationDTO, dishVariationEntity);
        Optional<Dish> dishObj = dishRepository.findById(dishVariationDTO.getDishDTO().getId());
        dishObj.orElseThrow(() -> new ResourceNotFoundException("Dish with id: " + dishVariationDTO.getDishDTO().getId() + " not found."));
        Dish dish = dishObj.get();
        dishVariationEntity.setDish(dish);
        dishVariationEntity = dishVariationRepository.save(dishVariationEntity);
        return new DishVariationDTO(dishVariationEntity);
    }

    @Transactional
    public DishVariationDTO update(Long id, DishVariationDTO dishVariationDTO) throws ResourceNotFoundException {
        try {
            DishVariation dishVariationEntity = dishVariationRepository.getReferenceById(id);
            copyDtoToEntity(dishVariationDTO, dishVariationEntity);
            dishVariationEntity = dishVariationRepository.save(dishVariationEntity);
            return new DishVariationDTO(dishVariationEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if (!dishVariationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            dishVariationRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
    
    private void copyDtoToEntity(DishVariationDTO dishVariationDTO, DishVariation dishVariationEntity){
        dishVariationEntity.setSku(dishVariationDTO.getSku());
        dishVariationEntity.setPortionSize(dishVariationDTO.getPortionSize());
        dishVariationEntity.setAdditionalPrice(dishVariationDTO.getAdditionalPrice());
    }
}
