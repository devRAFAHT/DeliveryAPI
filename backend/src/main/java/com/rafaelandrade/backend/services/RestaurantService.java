package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.entities.common.OperatingHours;
import com.rafaelandrade.backend.dto.MenuDTO;
import com.rafaelandrade.backend.dto.RestaurantCategoryDTO;
import com.rafaelandrade.backend.dto.RestaurantDTO;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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
            SetsAveragePrice(restaurant);
            setsAverageRating(restaurant);
            restaurant.setNumberOfReviews();
        }

        return restaurants.map(restaurant -> new RestaurantDTO(restaurant, restaurant.getMenus(), restaurant.getCategories(), restaurant.getOperatingHours()));
    }

    @Transactional(readOnly = true)
    public RestaurantDTO findByName(String name) throws ResourceNotFoundException {
        Optional<Restaurant> restaurantObj = restaurantRepository.findByCompanyName(name);
        Restaurant restaurantEntity = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        setIsOpen(restaurantEntity);
        SetsAveragePrice(restaurantEntity);
        setsAverageRating(restaurantEntity);
        restaurantEntity.setNumberOfReviews();
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional(readOnly = true)
    public RestaurantDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Restaurant> restaurantObj = restaurantRepository.findById(id);
        Restaurant restaurantEntity = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        setIsOpen(restaurantEntity);
        SetsAveragePrice(restaurantEntity);
        setsAverageRating(restaurantEntity);
        restaurantEntity.setNumberOfReviews();
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional
    public RestaurantDTO insert(RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        Restaurant restaurantEntity = new Restaurant();
        copyDtoToEntity(restaurantDTO, restaurantEntity);
        setIsOpen(restaurantEntity);
        SetsAveragePrice(restaurantEntity);
        setsAverageRating(restaurantEntity);
        restaurantEntity.setNumberOfReviews();
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        return new RestaurantDTO(restaurantEntity, restaurantEntity.getMenus(), restaurantEntity.getCategories(), restaurantEntity.getOperatingHours());
    }

    @Transactional
    public RestaurantDTO update(Long id, RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        try {
            Restaurant restaurantEntity = restaurantRepository.getReferenceById(id);
            copyDtoToEntity(restaurantDTO, restaurantEntity);
            setIsOpen(restaurantEntity);
            SetsAveragePrice(restaurantEntity);
            setsAverageRating(restaurantEntity);
            restaurantEntity.setNumberOfReviews();
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
        restaurantEntity.setTaxIdentificationNumber(restaurantDTO.getTaxIdentificationNumber());
        restaurantEntity.setCompanyName(restaurantDTO.getCompanyName());
        restaurantEntity.setBiography(restaurantEntity.getBiography());
        restaurantEntity.setPhoneNumber(restaurantDTO.getPhoneNumber());
        restaurantEntity.setProfilePictureUrl(restaurantEntity.getProfilePictureUrl());
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
            Menu menu = menuObj.get();
            restaurantEntity.getMenus().add(menu);

            menu.setLegalEntity(restaurantEntity);
        }

        restaurantEntity.getCategories().clear();
        for (RestaurantCategoryDTO restaurantCategoryDTO : restaurantDTO.getCategories()) {
            Optional<RestaurantCategory> categoryObj = restaurantCategoryRepository.findById(restaurantCategoryDTO.getId());
            categoryObj.orElseThrow(() -> new ResourceNotFoundException("Category with id " + restaurantCategoryDTO.getId() + " not found."));
            RestaurantCategory restaurantCategory = categoryObj.get();
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

    private void SetsAveragePrice(Restaurant restaurant) {
        BigDecimal priceOfAll = BigDecimal.valueOf(0.0);
        BigDecimal averagePrice;
        int quantityOfItems = 0;

        for (Menu menu : restaurant.getMenus()) {
            for (Item item : menu.getItems()) {
                priceOfAll = priceOfAll.add(item.getCurrentPrice());
                quantityOfItems++;

                if (item instanceof Dish) {
                    Dish dish = (Dish) item;
                    for (Additional additional : dish.getAdditional()) {
                        priceOfAll = priceOfAll.add(additional.getPrice());
                        quantityOfItems++;
                    }
                }
            }
        }

        if (quantityOfItems > 0) {
            averagePrice = priceOfAll.divide(BigDecimal.valueOf(quantityOfItems), 2, RoundingMode.HALF_UP);
            restaurant.setAveragePrice(averagePrice);
        } else {
            restaurant.setAveragePrice(BigDecimal.valueOf(0.00));
        }
    }

    private void setsAverageRating(Restaurant restaurant){
        if (restaurant.getAssessments().isEmpty()) {
            restaurant.setAverageRating(BigDecimal.ZERO);
            return;
        }

        int totalValue = 0;

        for (Assessment assessment : restaurant.getAssessments()) {
            totalValue += assessment.getPoints();
        }

        BigDecimal averageRating = BigDecimal.valueOf(totalValue).divide(BigDecimal.valueOf(restaurant.getAssessments().size()), RoundingMode.HALF_UP);

        restaurant.setAverageRating(averageRating);
    }

}