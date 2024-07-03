package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.common.OperatingHours;
import com.rafaelandrade.backend.dto.AdditionalDTO;
import com.rafaelandrade.backend.dto.MenuDTO;
import com.rafaelandrade.backend.dto.RestaurantCategoryDTO;
import com.rafaelandrade.backend.dto.RestaurantDTO;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.AdditionalRepository;
import com.rafaelandrade.backend.repositories.MenuRepository;
import com.rafaelandrade.backend.repositories.RestaurantCategoryRepository;
import com.rafaelandrade.backend.repositories.RestaurantRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelandrade.backend.util.CalculateDiscount;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    @Autowired private MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public Page<RestaurantDTO> findAll(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        return restaurants.map(restaurant -> new RestaurantDTO(restaurant, restaurant.getMenus(), restaurant.getCategories(), restaurant.getOperatingHours()));
    }

    @Transactional(readOnly = true)
    public RestaurantDTO findByName(String name) throws ResourceNotFoundException {
        Optional<Restaurant> restaurantObj = restaurantRepository.findByName(name);
        Restaurant restaurantEntity = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional(readOnly = true)
    public RestaurantDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Restaurant> restaurantObj = restaurantRepository.findById(id);
        Restaurant restaurantEntity = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional
    public RestaurantDTO insert(RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        Restaurant restaurantEntity = new Restaurant();
        copyDtoToEntity(restaurantDTO, restaurantEntity);
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional
    public RestaurantDTO update(Long id, RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        try {
            Restaurant restaurantEntity = restaurantRepository.getReferenceById(id);
            copyDtoToEntity(restaurantDTO, restaurantEntity);
            restaurantEntity = restaurantRepository.save(restaurantEntity);
            return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if(!restaurantRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            restaurantRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(RestaurantDTO restaurantDTO, Restaurant restaurantEntity) {
        restaurantEntity.setName(restaurantDTO.getName());
        restaurantEntity.setDescription(restaurantDTO.getDescription());
        restaurantEntity.setPhoneNumber(restaurantDTO.getPhoneNumber());
        restaurantEntity.setImgProfileUrl(restaurantDTO.getImgProfileUrl());
        restaurantEntity.setImgBackgroundUrl(restaurantDTO.getImgBackgroundUrl());
        restaurantEntity.setAveragePrice(restaurantDTO.getAveragePrice());
        restaurantEntity.setEstimatedDeliveryTime(restaurantDTO.getEstimatedDeliveryTime());
        restaurantEntity.setOpen(restaurantDTO.getOpen());
        restaurantEntity.setAddress(new Address(restaurantDTO.getAddress()));

        restaurantEntity.getMenus().clear();
        for (MenuDTO menuDTO : restaurantDTO.getMenus()) {
            Menu menu = menuRepository.getOne(menuDTO.getId());
            restaurantEntity.getMenus().add(menu);
        }

        restaurantEntity.getCategories().clear();
        for (RestaurantCategoryDTO restaurantCategoryDTO : restaurantDTO.getCategories()) {
            RestaurantCategory restaurantCategory = restaurantCategoryRepository.getOne(restaurantCategoryDTO.getId());
            restaurantEntity.getCategories().add(restaurantCategory);
        }

        restaurantEntity.getOperatingHours().clear();
        for (OperatingHours operatingHours : restaurantDTO.getOperatingHours()) {
            OperatingHours newOperatingHours = new OperatingHours();
            newOperatingHours.setDayOfWeek(operatingHours.getDayOfWeek());
            newOperatingHours.setOpeningTime(operatingHours.getOpeningTime());
            newOperatingHours.setClosingTime(operatingHours.getClosingTime());
            restaurantEntity.getOperatingHours().add(newOperatingHours);
        }
    }
}