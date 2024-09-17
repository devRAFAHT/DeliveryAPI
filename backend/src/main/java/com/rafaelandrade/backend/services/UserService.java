package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.*;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final BagRepository bagRepository;
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final LegalEntityRepository legalEntityRepository;
    private final ItemRepository itemRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(BagRepository bagRepository, UserRepository repository, RoleRepository roleRepository, AddressRepository addressRepository, LegalEntityRepository legalEntityRepository, ItemRepository itemRepository, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.bagRepository = bagRepository;
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;
        this.legalEntityRepository = legalEntityRepository;
        this.itemRepository = itemRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserDetailsResponseDTO> findAllPaged(Pageable pageable){
        logger.info("Finding all users with pageable: {}", pageable);
        Page<User> list = repository.findAll(pageable);
        return list.map(userEntity -> new UserDetailsResponseDTO(userEntity));
    }

    @Transactional(readOnly = true)
    public UserDetailsResponseDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding user by id: {}", id);
        Optional<User> obj = repository.findById(id);
        User userEntity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDetailsResponseDTO(userEntity);
    }

    @Transactional(readOnly = true)
    public UserDetailsResponseDTO findByUserName(String username) throws ResourceNotFoundException {
        logger.info("Finding user by username: {}", username);
        Optional<User> userObj = repository.findByUsername(username);
        User userEntity = userObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDetailsResponseDTO(userEntity);
    }

    @Transactional
    public UserDetailsResponseDTO insert(UserInsertDTO userInsertDTO) throws ResourceNotFoundException {
        logger.info("Inserting new user with details: {}", userInsertDTO);
        User userEntity = new User();
        checkIfAssociatedEntitiesExist(userInsertDTO);
        copyDtoToEntity(userInsertDTO, userEntity);
        userEntity.setCreatedAt(Instant.now());
        userEntity.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));

        Bag bag = new Bag(0, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        bag.getItems().clear();
        bag = bagRepository.save(bag);
        userEntity.setBag(bag);

        userEntity = repository.save(userEntity);
        return new UserDetailsResponseDTO(userEntity);
    }

    @Transactional
    public UserDetailsResponseDTO update(Long id, UserDTO userDTO) throws ResourceNotFoundException {
        logger.info("Updating user with id: {} and details: {}", id, userDTO);
        try {
            User userEntity = repository.getOne(id);
            checkIfAssociatedEntitiesExist(userDTO);
            copyDtoToEntity(userDTO, userEntity);
            userEntity.setUpdatedAt(Instant.now());
            userEntity = repository.save(userEntity);
            return new UserDetailsResponseDTO(userEntity);
        } catch (EntityNotFoundException e) {
            logger.error("User not found with id: {}", id, e);
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) throws DatabaseException, ResourceNotFoundException {
        logger.info("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            logger.error("User not found with id: {}", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting user with id: {}", id, e);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO userDTO, User userEntity) throws ResourceNotFoundException {
        logger.debug("Copying UserDTO to User entity: {}", userDTO);
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPersonalDocument(userDTO.getPersonalDocument());
        userEntity.setDateOfBirth(userDTO.getDateOfBirth());
        userEntity.setGender(userDTO.getGender());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        userEntity.setBiography(userDTO.getBiography());
        userEntity.setActive(userDTO.getActive());

        userEntity.getRoles().clear();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDTO.getId());
            userEntity.getRoles().add(role);
        }

        userEntity.getAddresses().clear();
        for (AddressDTO addressDTO : userDTO.getAddresses()) {
            Address address = addressRepository.getReferenceById(addressDTO.getId());
            userEntity.getAddresses().add(address);
        }

        userEntity.getFavoriteEstablishments().clear();
        for (LegalEntityCreateDTO legalEntityCreateDTO : userDTO.getFavoriteEstablishments()) {
            LegalEntity legalEntity = legalEntityRepository.getReferenceById(legalEntityCreateDTO.getId());
            userEntity.getFavoriteEstablishments().add(legalEntity);
        }

        userEntity.getFavoritesItems().clear();
        for (ItemDTO itemDTO : userDTO.getFavoritesItems()) {
            Item item = itemRepository.getReferenceById(itemDTO.getId());
            userEntity.getFavoritesItems().add(item);
        }
    }

    private void checkIfAssociatedEntitiesExist(UserDTO userDTO) throws ResourceNotFoundException {
        logger.debug("Checking if associated entities exist for UserDTO: {}", userDTO);
        for(AddressDTO addressDTO : userDTO.getAddresses()) {
            long addressId = addressDTO.getId();

            if (!addressRepository.existsById(addressId)) {
                logger.error("Address not found with id: {}", addressId);
                throw new ResourceNotFoundException("Address with id " + addressId + " not found.");
            }
        }

        for(LegalEntityCreateDTO legalEntityCreateDTO: userDTO.getFavoriteEstablishments()) {
            long establishmentId = legalEntityCreateDTO.getId();

            if (!legalEntityRepository.existsById(establishmentId)) {
                logger.error("LegalEntity not found with id: {}", establishmentId);
                throw new ResourceNotFoundException("LegalEntity with id " + establishmentId + " not found.");
            }
        }

        for(ItemDTO itemDTO : userDTO.getFavoritesItems()) {
            long itemId = itemDTO.getId();

            if (!itemRepository.existsById(itemId)) {
                logger.error("Item not found with id: {}", itemId);
                throw new ResourceNotFoundException("Item with id " + itemId + " not found.");
            }
        }
    }
}