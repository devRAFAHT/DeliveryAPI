package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.entities.common.ResidenceType;
import com.rafaelandrade.backend.dto.AddressDTO;
import com.rafaelandrade.backend.entities.Address;
import com.rafaelandrade.backend.repositories.AddressRepository;
import com.rafaelandrade.backend.services.exceptions.CountryNotSupportedException;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.PostalCodeNotFoundException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelandrade.backend.services.validation.PostalCodeValidatorManager;
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
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;
    private final PostalCodeValidatorManager postalCodeValidatorManager;

    public AddressService(AddressRepository addressRepository, PostalCodeValidatorManager postalCodeValidatorManager) {
        this.addressRepository = addressRepository;
        this.postalCodeValidatorManager = postalCodeValidatorManager;
    }

    @Transactional(readOnly = true)
    public Page<AddressDTO> findAll(Pageable pageable) {
        logger.info("Fetching all addresses with pagination: {}", pageable);
        Page<Address> listAddress = addressRepository.findAll(pageable);
        return listAddress.map(address -> new AddressDTO(address));
    }

    @Transactional(readOnly = true)
    public AddressDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Fetching address by id: {}", id);
        Optional<Address> addressObj = addressRepository.findById(id);
        Address addressEntity = addressObj.orElseThrow(() -> {
            logger.error("Address with id {} not found", id);
            return new ResourceNotFoundException("Entity not found");
        });
        return new AddressDTO(addressEntity);
    }

    @Transactional
    public AddressDTO insert(AddressDTO addressDTO) throws PostalCodeNotFoundException, CountryNotSupportedException {
        logger.info("Inserting new address with postal code: {}", addressDTO.getPostalCode());
        Address addressEntity = new Address();
        postalCodeValidatorManager.executePostalCodeValidator(addressDTO.getCountry(), addressDTO.getPostalCode());
        defineResidentialData(addressDTO);
        copyDtoToEntity(addressDTO, addressEntity);
        addressEntity = addressRepository.save(addressEntity);
        logger.info("Address inserted successfully with id: {}", addressEntity.getId());
        return new AddressDTO(addressEntity);
    }

    @Transactional
    public AddressDTO update(Long id, AddressDTO addressDTO) throws ResourceNotFoundException, PostalCodeNotFoundException, CountryNotSupportedException {
        try {
            logger.info("Updating address with id: {}", id);
            postalCodeValidatorManager.executePostalCodeValidator(addressDTO.getCountry(), addressDTO.getPostalCode());
            Address addressEntity = addressRepository.getReferenceById(id);
            defineResidentialData(addressDTO);
            copyDtoToEntity(addressDTO, addressEntity);
            addressEntity = addressRepository.save(addressEntity);
            logger.info("Address with id {} updated successfully", id);
            return new AddressDTO(addressEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Address with id {} not found for update", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting address with id: {}", id);
        if (!addressRepository.existsById(id)) {
            logger.error("Address with id {} not found for deletion", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            addressRepository.deleteById(id);
            logger.info("Address with id {} deleted successfully", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting address with id: {}", id);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AddressDTO addressDTO, Address address) {
        logger.debug("Copying data from AddressDTO to Address entity");
        address.setCountry(addressDTO.getCountry());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setState(addressDTO.getState());
        address.setCity(addressDTO.getCity());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setStreet(addressDTO.getStreet());
        address.setResidenceType(addressDTO.getResidenceType());
        address.setResidenceNumber(addressDTO.getResidenceNumber());
        address.setFloor(addressDTO.getFloor());
        address.setApartmentNumber(addressDTO.getApartmentNumber());
        address.setComplement(addressDTO.getComplement());
    }

    private void defineResidentialData(AddressDTO addressDTO) {
        logger.debug("Defining residential data for AddressDTO");
        if (addressDTO.getResidenceType() == ResidenceType.HOUSE) {
            addressDTO.setFloor(null);
            addressDTO.setApartmentNumber(null);
        } else {
            addressDTO.setFloor(addressDTO.getFloor());
            addressDTO.setApartmentNumber(addressDTO.getApartmentNumber());
        }
    }
}
