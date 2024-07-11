package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.*;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    private final RoleRepository roleRepository;

    private final AddressRepository addressRepository;

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    private final DrinkRepository drinkRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository repository, RoleRepository roleRepository, AddressRepository addressRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository, DrinkRepository drinkRepository, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.drinkRepository = drinkRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable){
        Page<User> list = repository.findAll(pageable);
        return list.map(userEntity -> new UserDTO(userEntity, userEntity.getAddresses(), userEntity.getFavoritesRestaurants(), userEntity.getFavoritesDishes(), userEntity.getFavoritesDrinks()));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) throws ResourceNotFoundException {
        Optional<User> obj = repository.findById(id);
        User userEntity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(userEntity, userEntity.getAddresses(), userEntity.getFavoritesRestaurants(), userEntity.getFavoritesDishes(), userEntity.getFavoritesDrinks());
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userInsertDTO) throws ResourceNotFoundException {
        User userEntity = new User();
        copyDtoToEntity(userInsertDTO, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
        userEntity = repository.save(userEntity);
        return new UserDTO(userEntity, userEntity.getAddresses(), userEntity.getFavoritesRestaurants(), userEntity.getFavoritesDishes(), userEntity.getFavoritesDrinks());
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) throws ResourceNotFoundException {
        try {
            User userEntity = repository.getOne(id);
            copyDtoToEntity(userDTO, userEntity);
            userEntity = repository.save(userEntity);
            return new UserDTO(userEntity, userEntity.getAddresses(), userEntity.getFavoritesRestaurants(), userEntity.getFavoritesDishes(), userEntity.getFavoritesDrinks());
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) throws DatabaseException, ResourceNotFoundException {
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            userRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO userDTO, User userEntity) throws ResourceNotFoundException {
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPersonalDocument(userDTO.getPersonalDocument());
        userEntity.setDateOfBirth(userDTO.getDateOfBirth());
        userEntity.setGender(userDTO.getGender());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        userEntity.setBiography(userDTO.getBiography());
        userEntity.setCreatedAt(Instant.now());
        userEntity.setUpdatedAt(Instant.now());
        userEntity.setActive(userDTO.getActive());

        userEntity.getRoles().clear();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDTO.getId());
            userEntity.getRoles().add(role);
        }

        userEntity.getAddresses().clear();
        for(AddressDTO addressDTO : userDTO.getAddresses()){
            Optional<Address> addressObj = addressRepository.findById(addressDTO.getId());
            addressObj.orElseThrow(() -> new ResourceNotFoundException("Address with id " + addressDTO.getId() + " not found."));
            Address address = addressObj.get();
            userEntity.getAddresses().add(address);
        }

        userEntity.getFavoritesRestaurants().clear();
        for (RestaurantDTO restaurantDTO : userDTO.getFavoritesRestaurants()) {
            Optional<Restaurant> restaurantObj = restaurantRepository.findById(restaurantDTO.getId());
            Restaurant restaurant = restaurantObj.orElseThrow(() -> new ResourceNotFoundException("Restaurant with id " + restaurantDTO.getId() + " not found."));
            userEntity.getFavoritesRestaurants().add(restaurant);
        }

        userEntity.getFavoritesDishes().clear();
        for (DishDTO dishDTO : userDTO.getFavoritesDishes()) {
            Optional<Dish> dishObj = dishRepository.findById(dishDTO.getId());
            Dish dish = dishObj.orElseThrow(() -> new ResourceNotFoundException("Dish with id " + dishDTO.getId() + " not found."));
            userEntity.getFavoritesDishes().add(dish);
        }

        userEntity.getFavoritesDrinks().clear();
        for (DrinkDTO drinkDTO : userDTO.getFavoritesDrinks()) {
            Optional<Drink> drinkObj = drinkRepository.findById(drinkDTO.getId());
            Drink drink = drinkObj.orElseThrow(() -> new ResourceNotFoundException("Drink with id " + drinkDTO.getId() + " not found."));
            userEntity.getFavoritesDrinks().add(drink);
        }

    }
}
