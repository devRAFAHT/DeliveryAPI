package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.common.ResidenceType;
import com.rafaelandrade.backend.dto.AddressDTO;
import com.rafaelandrade.backend.entities.Address;
import com.rafaelandrade.backend.repositories.AddressRepository;
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
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public Page<AddressDTO> findAll(Pageable pageable) {
        Page<Address> listAddress = addressRepository.findAll(pageable);
        return listAddress.map(address -> new AddressDTO(address));
    }

    @Transactional(readOnly = true)
    public AddressDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Address> addressObj = addressRepository.findById(id);
        Address addressEntity = addressObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AddressDTO(addressEntity);
    }

    @Transactional
    public AddressDTO insert(AddressDTO addressDTO) throws ResourceNotFoundException {
        Address addressEntity = new Address();
        copyDtoToEntity(addressDTO, addressEntity);
        addressEntity = addressRepository.save(addressEntity);
        return new AddressDTO(addressEntity);
    }

    @Transactional
    public AddressDTO update(Long id, AddressDTO addressDTO) throws ResourceNotFoundException {
        try {
            Address addressEntity = addressRepository.getReferenceById(id);
            copyDtoToEntity(addressDTO, addressEntity);
            addressEntity = addressRepository.save(addressEntity);
            return new AddressDTO(addressEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            addressRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AddressDTO addressDTO, Address address) {
        defineResidentialData(addressDTO);
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

    private void defineResidentialData(AddressDTO addressDTO){
        if(addressDTO.getResidenceType() == ResidenceType.HOUSE) {
            addressDTO.setFloor(null);
            addressDTO.setApartmentNumber(null);
        }else {
            addressDTO.setFloor(addressDTO.getFloor());
            addressDTO.setApartmentNumber(addressDTO.getApartmentNumber());
        }
    }

}
