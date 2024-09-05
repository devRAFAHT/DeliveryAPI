package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AdditionalCategoryDTO;
import com.rafaelandrade.backend.resources.exceptions.StandardError;
import com.rafaelandrade.backend.services.AdditionalCategoryService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
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

@Tag(name = "Additional Categories", description = "Endpoints for managing additional categories.")
@RestController
@RequestMapping(value = "/additional-categories")
public class AdditionalCategoryResource {

    private final AdditionalCategoryService additionalCategoryService;

    @Autowired
    public AdditionalCategoryResource(AdditionalCategoryService additionalCategoryService) {
        this.additionalCategoryService = additionalCategoryService;
    }

    @Operation(summary = "List all additional categories", description = "Resource to list all additional categories.", responses = {
            @ApiResponse(responseCode = "200", description = "List of additional categories returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping
    public ResponseEntity<Page<AdditionalCategoryDTO>> findAll(Pageable pageable) {
        Page<AdditionalCategoryDTO> categories = additionalCategoryService.findAll(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @Operation(summary = "Search for an additional category by ID", description = "Resource to search for an existing additional category by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Additional category found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Additional category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AdditionalCategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AdditionalCategoryDTO additionalCategoryDTO = additionalCategoryService.findById(id);
        return ResponseEntity.ok().body(additionalCategoryDTO);
    }

    @Operation(summary = "Search for an additional category by Name", description = "Resource to search for an existing additional category by its Name.", responses = {
            @ApiResponse(responseCode = "200", description = "Additional category found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Additional category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<AdditionalCategoryDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        AdditionalCategoryDTO additionalCategoryDTO = additionalCategoryService.findByName(name);
        return ResponseEntity.ok().body(additionalCategoryDTO);
    }

    @Operation(summary = "Create a new additional category", description = "Resource to create a new additional category.", responses = {
            @ApiResponse(responseCode = "201", description = "Additional category created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdditionalCategoryDTO.class))),
            @ApiResponse(responseCode = "409", description = "The additional category is already registered in the system.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Category not registered due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<AdditionalCategoryDTO> insert(@Valid @RequestBody AdditionalCategoryDTO additionalCategoryDTO) {
        additionalCategoryDTO = additionalCategoryService.insert(additionalCategoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(additionalCategoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(additionalCategoryDTO);
    }

    @Operation(summary = "Update the name of an additional category", description = "Resource to update the name of an additional category.", responses = {
            @ApiResponse(responseCode = "204", description = "Additional category updated successfully."),
            @ApiResponse(responseCode = "404", description = "Additional category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<AdditionalCategoryDTO> update(@PathVariable Long id, @Valid @RequestBody AdditionalCategoryDTO additionalCategoryDTO) throws ResourceNotFoundException {
        additionalCategoryDTO = additionalCategoryService.update(id, additionalCategoryDTO);
        return ResponseEntity.ok().body(additionalCategoryDTO);
    }

    @Operation(
            summary = "Delete an additional category",
            description = "Delete an additional category by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Additional category deleted successfully."),
                    @ApiResponse(responseCode = "404", description = "Additional category not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
                    @ApiResponse(responseCode = "500", description = "Database error.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
            }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        additionalCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}