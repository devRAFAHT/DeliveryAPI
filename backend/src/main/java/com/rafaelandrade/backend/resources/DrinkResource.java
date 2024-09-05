package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DrinkDTO;
import com.rafaelandrade.backend.services.DrinkService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Drink", description = "Endpoints for managing drinks.")
@RestController
@RequestMapping(value = "/drinks")
public class DrinkResource {

    private final DrinkService drinkService;

    public DrinkResource(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @Operation(summary = "List all drinks", description = "Retrieve a paginated list of all drinks.", responses = {
            @ApiResponse(responseCode = "200", description = "List of drinks returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<DrinkDTO>> findAll(Pageable pageable) {
        Page<DrinkDTO> drinks = drinkService.findAll(pageable);
        return ResponseEntity.ok().body(drinks);
    }

    @Operation(summary = "Find a drink by ID", description = "Retrieve a drink by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Drink found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkDTO.class))),
            @ApiResponse(responseCode = "404", description = "Drink not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DrinkDTO drinkDTO = drinkService.findById(id);
        return ResponseEntity.ok().body(drinkDTO);
    }

    @Operation(summary = "Find a drink by name", description = "Retrieve a drink by its name.", responses = {
            @ApiResponse(responseCode = "200", description = "Drink found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkDTO.class))),
            @ApiResponse(responseCode = "404", description = "Drink not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<DrinkDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        DrinkDTO drinkDTO = drinkService.findByName(name);
        return ResponseEntity.ok().body(drinkDTO);
    }

    @Operation(summary = "Create a new drink", description = "Add a new drink to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Drink created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<DrinkDTO> insert(@Valid @RequestBody DrinkDTO drinkDTO) throws ResourceNotFoundException {
        drinkDTO = drinkService.insert(drinkDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(drinkDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(drinkDTO);
    }

    @Operation(summary = "Update an existing drink", description = "Modify an existing drink using its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Drink updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkDTO.class))),
            @ApiResponse(responseCode = "404", description = "Drink not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<DrinkDTO> update(@Valid @PathVariable Long id, @RequestBody DrinkDTO drinkDTO) throws ResourceNotFoundException {
        drinkDTO = drinkService.update(id, drinkDTO);
        return ResponseEntity.ok().body(drinkDTO);
    }

    @Operation(summary = "Delete a drink", description = "Remove a drink from the system using its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Drink deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Drink not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        drinkService.delete(id);
        return ResponseEntity.noContent().build();
    }
}