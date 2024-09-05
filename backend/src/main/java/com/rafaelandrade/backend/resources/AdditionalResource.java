package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AdditionalDTO;
import com.rafaelandrade.backend.services.AdditionalService;
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

@Tag(name = "Additional Items", description = "Endpoints for managing additional items for dishes.")
@RestController
@RequestMapping(value = "/additional")
public class AdditionalResource {

    private final AdditionalService additionalService;

    @Autowired
    public AdditionalResource(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }

    @Operation(summary = "List all additional items", description = "Resource to list all additional items for dishes.", responses = {
            @ApiResponse(responseCode = "200", description = "List of additional items returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdditionalDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<AdditionalDTO>> findAll(Pageable pageable) {
        Page<AdditionalDTO> additionalList = additionalService.findAll(pageable);
        return ResponseEntity.ok().body(additionalList);
    }

    @Operation(summary = "Search for an additional item by ID", description = "Resource to search for an existing additional item by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Additional item found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdditionalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Additional item not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AdditionalDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AdditionalDTO additionalDTO = additionalService.findById(id);
        return ResponseEntity.ok().body(additionalDTO);
    }

    @Operation(summary = "Search for an additional item by Name", description = "Resource to search for an existing additional item by its Name.", responses = {
            @ApiResponse(responseCode = "200", description = "Additional item found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdditionalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Additional item not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<AdditionalDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        AdditionalDTO additionalDTO = additionalService.findByName(name);
        return ResponseEntity.ok().body(additionalDTO);
    }

    @Operation(summary = "Create a new additional item", description = "Resource to create a new additional item for a dish.", responses = {
            @ApiResponse(responseCode = "201", description = "Additional item created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdditionalDTO.class))),
            @ApiResponse(responseCode = "409", description = "The additional item is already registered in the system.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "422", description = "Additional item not created due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<AdditionalDTO> insert(@Valid @RequestBody AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        additionalDTO = additionalService.insert(additionalDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(additionalDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(additionalDTO);
    }

    @Operation(summary = "Update an additional item", description = "Resource to update an existing additional item for a dish.", responses = {
            @ApiResponse(responseCode = "200", description = "Additional item updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdditionalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Additional item not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<AdditionalDTO> update(@PathVariable Long id, @Valid @RequestBody AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        additionalDTO = additionalService.update(id, additionalDTO);
        return ResponseEntity.ok().body(additionalDTO);
    }

    @Operation(summary = "Delete an additional item", description = "Delete an additional item by its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Additional item deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Additional item not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        additionalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
