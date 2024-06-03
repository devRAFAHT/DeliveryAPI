package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DishDTO;
import com.rafaelandrade.backend.entities.DishCategory;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.repositories.DishCategoryRepository;
import com.rafaelandrade.backend.repositories.DishRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    @Transactional(readOnly = true)
    public Page<DishDTO> findAll(Pageable pageable) {
        Page<Dish> dishes = dishRepository.findAll(pageable);
        return dishes.map(dish -> new DishDTO(dish));
    }

    @Transactional(readOnly = true)
    public DishDTO findByName(String name) throws ResourceNotFoundException {
        Optional<Dish> dishObj = dishRepository.findByName(name);
        Dish dishEntity = dishObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DishDTO(dishEntity);
    }

    @Transactional(readOnly = true)
    public DishDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Dish> dishObj = dishRepository.findById(id);
        Dish dishEntity = dishObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DishDTO(dishEntity);
    }

    @Transactional
    public DishDTO insert(DishDTO dishDTO) throws ResourceNotFoundException {
        Optional<DishCategory> categoryObj = dishCategoryRepository.findById(dishDTO.getCategory().getId());
        DishCategory dishCategoryEntity = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Dish dishEntity = new Dish();
        copyDtoToEntity(dishDTO, dishEntity);
        dishEntity = dishRepository.save(dishEntity);
        return new DishDTO(dishEntity);
    }

    @Transactional
    public DishDTO update(Long id, DishDTO dishDTO) throws ResourceNotFoundException {
        try {
            Dish dishEntity = dishRepository.getReferenceById(id);
            copyDtoToEntity(dishDTO, dishEntity);
            dishEntity = dishRepository.save(dishEntity);
            return new DishDTO(dishEntity);
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

    private void copyDtoToEntity(DishDTO dishDTO, Dish dishEntity){
        dishEntity.setName(dishDTO.getName());
        dishEntity.setDescription(dishDTO.getDescription());
        dishEntity.setImgUrl(dishDTO.getImgUrl());
        dishEntity.setPrice(dishDTO.getPrice());
        dishEntity.setPortionSize(dishDTO.getPortionSize());
        dishEntity.setPreparationTime(dishDTO.getPreparationTime());
        dishEntity.setCategory(dishDTO.getCategory());
    }

}