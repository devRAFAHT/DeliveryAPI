package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.RestaurantDetailsResponseDTO;
import com.rafaelandrade.backend.entities.common.OperatingHours;
import com.rafaelandrade.backend.dto.MenuDTO;
import com.rafaelandrade.backend.dto.RestaurantCategoryDTO;
import com.rafaelandrade.backend.dto.RestaurantCreateDTO;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    private final RestaurantRepository restaurantRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final MenuRepository menuRepository;
    private final AddressRepository addressRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantCategoryRepository restaurantCategoryRepository, MenuRepository menuRepository, AddressRepository addressRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.menuRepository = menuRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public Page<RestaurantDetailsResponseDTO> findAll(Pageable pageable) {
        logger.debug("Finding all restaurants with pageable: {}", pageable);
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);

        for(Restaurant restaurant : restaurants){
            setIsOpen(restaurant);
            SetsAveragePrice(restaurant);
            setsAverageRating(restaurant);
            restaurant.setNumberOfReviews();
        }

        logger.info("Found {} restaurants", restaurants.getTotalElements());
        return restaurants.map(restaurant -> new RestaurantDetailsResponseDTO(restaurant, restaurant.getCategories()));
    }

    @Transactional(readOnly = true)
    public RestaurantDetailsResponseDTO findByName(String name) throws ResourceNotFoundException {
        logger.debug("Finding restaurant by name: {}", name);
        Optional<Restaurant> restaurantObj = restaurantRepository.findByCompanyName(name);
        Restaurant restaurantEntity = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        setIsOpen(restaurantEntity);
        SetsAveragePrice(restaurantEntity);
        setsAverageRating(restaurantEntity);
        restaurantEntity.setNumberOfReviews();
        return new RestaurantDetailsResponseDTO(restaurantEntity, restaurantEntity.getCategories());
    }

    @Transactional(readOnly = true)
    public RestaurantDetailsResponseDTO findById(Long id) throws ResourceNotFoundException {
        logger.debug("Finding restaurant by id: {}", id);
        Optional<Restaurant> restaurantObj = restaurantRepository.findById(id);
        Restaurant restaurantEntity = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        setIsOpen(restaurantEntity);
        SetsAveragePrice(restaurantEntity);
        setsAverageRating(restaurantEntity);
        restaurantEntity.setNumberOfReviews();
        return new RestaurantDetailsResponseDTO(restaurantEntity, restaurantEntity.getCategories());
    }

    @Transactional
    public RestaurantDetailsResponseDTO insert(RestaurantCreateDTO restaurantCreateDTO) throws ResourceNotFoundException {
        logger.debug("Inserting new restaurant: {}", restaurantCreateDTO);
        Restaurant restaurantEntity = new Restaurant();
        copyDtoToEntity(restaurantCreateDTO, restaurantEntity);
        setIsOpen(restaurantEntity);
        SetsAveragePrice(restaurantEntity);
        setsAverageRating(restaurantEntity);
        restaurantEntity.setNumberOfReviews();
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        return new RestaurantDetailsResponseDTO(restaurantEntity, restaurantEntity.getCategories());
    }

    @Transactional
    public RestaurantDetailsResponseDTO update(Long id, RestaurantCreateDTO restaurantCreateDTO) throws ResourceNotFoundException {
        logger.debug("Updating restaurant with id: {}", id);
        try {
            Restaurant restaurantEntity = restaurantRepository.getReferenceById(id);
            copyDtoToEntity(restaurantCreateDTO, restaurantEntity);
            setIsOpen(restaurantEntity);
            SetsAveragePrice(restaurantEntity);
            setsAverageRating(restaurantEntity);
            restaurantEntity.setNumberOfReviews();
            restaurantEntity = restaurantRepository.save(restaurantEntity);
            return new RestaurantDetailsResponseDTO(restaurantEntity, restaurantEntity.getCategories());
        } catch (EntityNotFoundException e) {
            logger.error("Restaurant with id {} not found", id, e);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.debug("Deleting restaurant with id: {}", id);
        if (!restaurantRepository.existsById(id)) {
            logger.error("Restaurant with id {} not found for deletion", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            restaurantRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while deleting restaurant with id {}", id, e);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(RestaurantCreateDTO restaurantCreateDTO, Restaurant restaurantEntity) throws ResourceNotFoundException {
        logger.debug("Copying DTO to entity: {}", restaurantCreateDTO);
        restaurantEntity.setTaxIdentificationNumber(restaurantCreateDTO.getTaxIdentificationNumber());
        restaurantEntity.setCompanyName(restaurantCreateDTO.getCompanyName());
        restaurantEntity.setBiography(restaurantEntity.getBiography());
        restaurantEntity.setPhoneNumber(restaurantCreateDTO.getPhoneNumber());
        restaurantEntity.setProfilePictureUrl(restaurantCreateDTO.getImgProfileUrl());
        restaurantEntity.setImgBackgroundUrl(restaurantCreateDTO.getImgBackgroundUrl());
        restaurantEntity.setAveragePrice(restaurantCreateDTO.getAveragePrice());
        restaurantEntity.setEstimatedDeliveryTime(restaurantCreateDTO.getEstimatedDeliveryTime());

        checkIfAssociatedEntitiesExist(restaurantCreateDTO);

        restaurantEntity.setAddress(new Address(restaurantCreateDTO.getAddress()));

        restaurantEntity.getMenus().clear();
        for (MenuDTO menuDTO : restaurantCreateDTO.getMenus()) {
            Menu menu = menuRepository.getReferenceById(menuDTO.getId());
            restaurantEntity.getMenus().add(menu);
            menu.setLegalEntity(restaurantEntity);
        }

        restaurantEntity.getCategories().clear();
        for (RestaurantCategoryDTO restaurantCategoryDTO : restaurantCreateDTO.getCategories()) {
            RestaurantCategory restaurantCategory = restaurantCategoryRepository.getReferenceById(restaurantCategoryDTO.getId());
            restaurantEntity.getCategories().add(restaurantCategory);
        }

        restaurantEntity.getOperatingHours().clear();
        for (OperatingHours operatingHours : restaurantCreateDTO.getOperatingHours()) {
            OperatingHours newOperatingHours = new OperatingHours();
            newOperatingHours.setDayOfWeek(operatingHours.getDayOfWeek());
            newOperatingHours.setOpeningTime(operatingHours.getOpeningTime());
            newOperatingHours.setClosingTime(operatingHours.getClosingTime());
            restaurantEntity.getOperatingHours().add(newOperatingHours);
        }
    }

    private void setIsOpen(Restaurant restaurant){
        logger.debug("Setting open status for restaurant: {}", restaurant.getId());
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
        logger.debug("Calculating average price for restaurant: {}", restaurant.getId());
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
        logger.debug("Calculating average rating for restaurant: {}", restaurant.getId());
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

    private void checkIfAssociatedEntitiesExist(RestaurantCreateDTO restaurantCreateDTO) throws ResourceNotFoundException {
        logger.debug("Checking existence of associated entities");
        if (!restaurantCategoryRepository.existsById(restaurantCreateDTO.getCategories().get(0).getId())) {
            throw new ResourceNotFoundException("Restaurant category not found");
        }
        if (!addressRepository.existsById(restaurantCreateDTO.getAddress().getId())) {
            throw new ResourceNotFoundException("Address not found");
        }
        if (!menuRepository.existsById(restaurantCreateDTO.getMenus().get(0).getId())) {
            throw new ResourceNotFoundException("Menu not found");
        }
    }
}