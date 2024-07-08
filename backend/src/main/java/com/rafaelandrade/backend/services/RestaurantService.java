package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.common.OperatingHours;
import com.rafaelandrade.backend.dto.AdditionalDTO;
import com.rafaelandrade.backend.dto.MenuDTO;
import com.rafaelandrade.backend.dto.RestaurantCategoryDTO;
import com.rafaelandrade.backend.dto.RestaurantDTO;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
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
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rafaelandrade.backend.common.ResidenceType.HOUSE;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantCategoryRepository restaurantCategoryRepository;

    private final  MenuRepository menuRepository;

    private final AddressRepository addressRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantCategoryRepository restaurantCategoryRepository, MenuRepository menuRepository, AddressRepository addressRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.menuRepository = menuRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public Page<RestaurantDTO> findAll(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);

        for(Restaurant restaurant : restaurants){
            setIsOpen(restaurant);
        }

        return restaurants.map(restaurant -> new RestaurantDTO(restaurant, restaurant.getMenus(), restaurant.getCategories(), restaurant.getOperatingHours()));
    }

    @Transactional(readOnly = true)
    public RestaurantDTO findByName(String name) throws ResourceNotFoundException {
        Optional<Restaurant> restaurantObj = restaurantRepository.findByName(name);
        Restaurant restaurantEntity = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        setIsOpen(restaurantEntity);
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional(readOnly = true)
    public RestaurantDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Restaurant> restaurantObj = restaurantRepository.findById(id);
        Restaurant restaurantEntity = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        setIsOpen(restaurantEntity);
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional
    public RestaurantDTO insert(RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        Restaurant restaurantEntity = new Restaurant();
        copyDtoToEntity(restaurantDTO, restaurantEntity);
        setIsOpen(restaurantEntity);
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional
    public RestaurantDTO update(Long id, RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        try {
            Restaurant restaurantEntity = restaurantRepository.getReferenceById(id);
            copyDtoToEntity(restaurantDTO, restaurantEntity);
            setIsOpen(restaurantEntity);
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

    private void copyDtoToEntity(RestaurantDTO restaurantDTO, Restaurant restaurantEntity) throws ResourceNotFoundException {
        restaurantEntity.setName(restaurantDTO.getName());
        restaurantEntity.setDescription(restaurantDTO.getDescription());
        restaurantEntity.setPhoneNumber(restaurantDTO.getPhoneNumber());
        restaurantEntity.setImgProfileUrl(restaurantDTO.getImgProfileUrl());
        restaurantEntity.setImgBackgroundUrl(restaurantDTO.getImgBackgroundUrl());
        restaurantEntity.setAveragePrice(restaurantDTO.getAveragePrice());
        restaurantEntity.setEstimatedDeliveryTime(restaurantDTO.getEstimatedDeliveryTime());

        Optional<Address> addressObj = addressRepository.findById(restaurantDTO.getAddress().getId());
        addressObj.orElseThrow(() -> new ResourceNotFoundException("Address with id " + restaurantDTO.getAddress().getId() + " not found."));

        restaurantEntity.setAddress(new Address(addressObj.get()));

        restaurantEntity.getMenus().clear();
        for (MenuDTO menuDTO : restaurantDTO.getMenus()) {
            Optional<Menu> menuObj = menuRepository.findById(menuDTO.getId());
            menuObj.orElseThrow(() -> new ResourceNotFoundException("Menu with id " + menuDTO.getId() + " not found."));
            Menu menu = menuRepository.getOne(menuDTO.getId());
            restaurantEntity.getMenus().add(menu);
        }

        restaurantEntity.getCategories().clear();
        for (RestaurantCategoryDTO restaurantCategoryDTO : restaurantDTO.getCategories()) {
            Optional<RestaurantCategory> categoryObj = restaurantCategoryRepository.findById(restaurantCategoryDTO.getId());
            categoryObj.orElseThrow(() -> new ResourceNotFoundException("Category with id " + restaurantCategoryDTO.getId() + " not found."));
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

    private void setIsOpen(Restaurant restaurant){
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDay = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        for (OperatingHours hours : restaurant.getOperatingHours()) {
            if (hours.getDayOfWeek() == currentDay) {
                LocalTime openingTime = hours.getOpeningTime();
                LocalTime closingTime = hours.getClosingTime();

                if ((currentTime.equals(openingTime) || currentTime.isAfter(openingTime)) &&
                        (currentTime.equals(closingTime) || currentTime.isBefore(closingTime))) {
                    restaurant.setOpen(true);
                    return;
                }
            }
        }
        restaurant.setOpen(false);
    }

}