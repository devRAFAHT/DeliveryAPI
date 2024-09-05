package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DrinkCategoryDTO;
import com.rafaelandrade.backend.resources.exceptions.StandardError;
import com.rafaelandrade.backend.services.DrinkCategoryService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Drink Categories", description = "Endpoints for managing drink categories.")
@RestController
@RequestMapping(value = "/drink-categories")
public class DrinkCategoryResource {

    private final DrinkCategoryService drinkCategoryService;

    @Autowired
    public DrinkCategoryResource(DrinkCategoryService drinkCategoryService) {
        this.drinkCategoryService = drinkCategoryService;
    }

    @Operation(summary = "List all drink categories", description = "Resource to list all drink categories.", responses = {
            @ApiResponse(responseCode = "200", description = "List of drink categories returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkCategoryDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<DrinkCategoryDTO>> findAll(Pageable pageable){
        Page<DrinkCategoryDTO> categories = drinkCategoryService.findAll(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @Operation(summary = "Search for a drink category by ID", description = "Resource to search for an existing drink category by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Drink category found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Drink category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkCategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DrinkCategoryDTO drinkCategoryDTO = drinkCategoryService.findById(id);
        return ResponseEntity.ok().body(drinkCategoryDTO);
    }

    @Operation(summary = "Search for a drink category by Name", description = "Resource to search for an existing drink category by its Name.", responses = {
            @ApiResponse(responseCode = "200", description = "Drink category found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Drink category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<DrinkCategoryDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        DrinkCategoryDTO drinkCategoryDTO = drinkCategoryService.findByName(name);
        return ResponseEntity.ok().body(drinkCategoryDTO);
    }

    @Operation(summary = "Create a new drink category", description = "Resource to create a new drink category.", responses = {
            @ApiResponse(responseCode = "201", description = "Drink category created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkCategoryDTO.class))),
            @ApiResponse(responseCode = "409", description = "The drink category is already registered in the system.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Category not registered due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<DrinkCategoryDTO> insert(@Valid @RequestBody DrinkCategoryDTO drinkCategoryDTO) {
        drinkCategoryDTO = drinkCategoryService.insert(drinkCategoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(drinkCategoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(drinkCategoryDTO);
    }

    @Operation(summary = "Update the name of a drink category", description = "Resource to update the name of a drink category.", responses = {
            @ApiResponse(responseCode = "200", description = "Drink category updated successfully."),
            @ApiResponse(responseCode = "404", description = "Drink category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<DrinkCategoryDTO> update(@PathVariable Long id, @Valid @RequestBody DrinkCategoryDTO drinkCategoryDTO) throws ResourceNotFoundException {
        drinkCategoryDTO = drinkCategoryService.update(id, drinkCategoryDTO);
        return ResponseEntity.ok().body(drinkCategoryDTO);
    }

    @Operation(summary = "Delete a drink category", description = "Delete a drink category by its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Drink category deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Drink category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        drinkCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}