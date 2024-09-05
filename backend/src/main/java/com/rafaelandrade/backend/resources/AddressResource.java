package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AddressDTO;
import com.rafaelandrade.backend.services.AddressService;
import com.rafaelandrade.backend.services.exceptions.CountryNotSupportedException;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.PostalCodeNotFoundException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Address", description = "Endpoints for managing addresses.")
@RestController
@RequestMapping(value = "/address")
public class AddressResource {

    private final AddressService addressService;

    @Autowired
    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "List all addresses", description = "Resource to list all addresses.", responses = {
            @ApiResponse(responseCode = "200", description = "List of addresses returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<AddressDTO>> findAll(Pageable pageable) {
        Page<AddressDTO> listAddress = addressService.findAll(pageable);
        return ResponseEntity.ok().body(listAddress);
    }

    @Operation(summary = "Search for an address by ID", description = "Resource to search for an address by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Address found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "404", description = "Address not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AddressDTO addressDTO = addressService.findById(id);
        return ResponseEntity.ok().body(addressDTO);
    }

    @Operation(summary = "Create a new address", description = "Resource to create a new address.", responses = {
            @ApiResponse(responseCode = "201", description = "Address created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "404", description = "Postal code or country not supported.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostalCodeNotFoundException.class))),
            @ApiResponse(responseCode = "422", description = "Address not created due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryNotSupportedException.class)))
    })
    @PostMapping
    public ResponseEntity<AddressDTO> insert(@Valid @RequestBody AddressDTO addressDTO) throws PostalCodeNotFoundException, CountryNotSupportedException {
        addressDTO = addressService.insert(addressDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addressDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(addressDTO);
    }

    @Operation(summary = "Update an address", description = "Resource to update an existing address.", responses = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "404", description = "Address not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "422", description = "Address not updated due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostalCodeNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @Valid @RequestBody AddressDTO addressDTO)
            throws ResourceNotFoundException, PostalCodeNotFoundException, CountryNotSupportedException {
        addressDTO = addressService.update(id, addressDTO);
        return ResponseEntity.ok().body(addressDTO);
    }

    @Operation(summary = "Delete an address", description = "Delete an address by its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Address deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Address not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}