package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.*;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
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
        Page<User> list = repository.findAll(pageable);
        return list.map(userEntity -> new UserDetailsResponseDTO(userEntity));
    }

    @Transactional(readOnly = true)
    public UserDetailsResponseDTO findById(Long id) throws ResourceNotFoundException {
        Optional<User> obj = repository.findById(id);
        User userEntity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDetailsResponseDTO(userEntity);
    }

    @Transactional(readOnly = true)
    public UserDetailsResponseDTO findByUserName(String username) throws ResourceNotFoundException {
        Optional<User> userObj = repository.findByUsername(username);
        User userEntity = userObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDetailsResponseDTO(userEntity);
    }

    @Transactional
    public UserDetailsResponseDTO insert(UserInsertDTO userInsertDTO) throws ResourceNotFoundException {
        User userEntity = new User();
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
        try {
            User userEntity = repository.getOne(id);
            copyDtoToEntity(userDTO, userEntity);
            userEntity.setUpdatedAt(Instant.now());
            userEntity = repository.save(userEntity);
            return new UserDetailsResponseDTO(userEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) throws DatabaseException, ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO userDTO, User userEntity) throws ResourceNotFoundException {
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
            Optional<Address> addressObj = addressRepository.findById(addressDTO.getId());
            addressObj.orElseThrow(() -> new ResourceNotFoundException("Address with id " + addressDTO.getId() + " not found."));
            Address address = addressObj.get();
            userEntity.getAddresses().add(address);
        }

        userEntity.getFavoriteEstablishments().clear();
        for (LegalEntityCreateDTO legalEntityCreateDTO : userDTO.getFavoriteEstablishments()) {
            Optional<LegalEntity> legalEntityObj = legalEntityRepository.findById(legalEntityCreateDTO.getId());
            LegalEntity legalEntity = legalEntityObj.orElseThrow(() -> new ResourceNotFoundException("LegalEntity with id " + legalEntityCreateDTO.getId() + " not found."));
            userEntity.getFavoriteEstablishments().add(legalEntity);
        }

        userEntity.getFavoritesItems().clear();
        for (ItemDTO itemDTO : userDTO.getFavoritesItems()) {
            Optional<Item> itemObj = itemRepository.findById(itemDTO.getId());
            Item item = itemObj.orElseThrow(() -> new ResourceNotFoundException("Item with id " + itemDTO.getId() + " not found."));
            userEntity.getFavoritesItems().add(item);
        }
    }
}
