package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AddressDTO;
import com.rafaelandrade.backend.services.AddressService;
import com.rafaelandrade.backend.services.exceptions.CountryNotSupportedException;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.PostalCodeNotFoundException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/address")
public class AddressResource {

    @Autowired
    private AddressService addressService;

    @GetMapping
    ResponseEntity<Page<AddressDTO>> findAll(Pageable pageable){
        Page<AddressDTO> listAddress = addressService.findAll(pageable);
        return ResponseEntity.ok().body(listAddress);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<AddressDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AddressDTO addressDTO = addressService.findById(id);
        return ResponseEntity.ok().body(addressDTO);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> insert(@Valid @RequestBody AddressDTO addressDTO) throws PostalCodeNotFoundException, CountryNotSupportedException {
        addressDTO = addressService.insert(addressDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addressDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(addressDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> update(@Valid @PathVariable Long id, @RequestBody AddressDTO addressDTO) throws ResourceNotFoundException, PostalCodeNotFoundException, CountryNotSupportedException {
        addressDTO = addressService.update(id, addressDTO);
        return ResponseEntity.ok().body(addressDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
