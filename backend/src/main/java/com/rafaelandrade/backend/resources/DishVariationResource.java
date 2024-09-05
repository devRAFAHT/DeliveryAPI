package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DishVariationDTO;
import com.rafaelandrade.backend.services.DishVariationService;
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

@Tag(name = "DishVariation", description = "Endpoints for managing dish variations.")
@RestController
@RequestMapping(value = "/dishVariations")
public class DishVariationResource {

    private final DishVariationService dishVariationService;

    public DishVariationResource(DishVariationService dishVariationService) {
        this.dishVariationService = dishVariationService;
    }

    @Operation(summary = "List all dish variations", description = "Retrieve a paginated list of all dish variations.", responses = {
            @ApiResponse(responseCode = "200", description = "List of dish variations returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishVariationDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<DishVariationDTO>> findAll(Pageable pageable) {
        Page<DishVariationDTO> dishVariations = dishVariationService.findAll(pageable);
        return ResponseEntity.ok().body(dishVariations);
    }

    @Operation(summary = "Find a dish variation by ID", description = "Retrieve a dish variation by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Dish variation found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishVariationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dish variation not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<DishVariationDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DishVariationDTO dishVariationDTO = dishVariationService.findById(id);
        return ResponseEntity.ok().body(dishVariationDTO);
    }

    @Operation(summary = "Create a new dish variation", description = "Add a new dish variation to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Dish variation created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishVariationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<DishVariationDTO> insert(@Valid @RequestBody DishVariationDTO dishVariationDTO) throws ResourceNotFoundException {
        dishVariationDTO = dishVariationService.insert(dishVariationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dishVariationDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(dishVariationDTO);
    }

    @Operation(summary = "Update an existing dish variation", description = "Modify an existing dish variation using its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Dish variation updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishVariationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dish variation not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<DishVariationDTO> update(@Valid @PathVariable Long id, @RequestBody DishVariationDTO dishVariationDTO) throws ResourceNotFoundException {
        dishVariationDTO = dishVariationService.update(id, dishVariationDTO);
        return ResponseEntity.ok().body(dishVariationDTO);
    }

    @Operation(summary = "Delete a dish variation", description = "Remove a dish variation from the system using its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Dish variation deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Dish variation not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        dishVariationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}