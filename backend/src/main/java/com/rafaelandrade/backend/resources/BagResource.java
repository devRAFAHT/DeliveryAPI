package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.BagDTO;
import com.rafaelandrade.backend.services.BagService;
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

@Tag(name = "Bag", description = "Endpoints for managing bags.")
@RestController
@RequestMapping(value = "/bags")
public class BagResource {

    private final BagService bagService;

    public BagResource(BagService bagService) {
        this.bagService = bagService;
    }

    @Operation(summary = "List all bags", description = "Retrieve a paginated list of all bags.", responses = {
            @ApiResponse(responseCode = "200", description = "List of bags returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BagDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<BagDTO>> findAll(Pageable pageable) {
        Page<BagDTO> bags = bagService.findAll(pageable);
        return ResponseEntity.ok().body(bags);
    }

    @Operation(summary = "Find a bag by ID", description = "Retrieve a bag by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Bag found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BagDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bag not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<BagDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        BagDTO bagDTO = bagService.findById(id);
        return ResponseEntity.ok().body(bagDTO);
    }

    @Operation(summary = "Create a new bag", description = "Add a new bag to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Bag created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BagDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<BagDTO> insert(@Valid @RequestBody BagDTO bagDTO) throws ResourceNotFoundException {
        bagDTO = bagService.insert(bagDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bagDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(bagDTO);
    }

    @Operation(summary = "Update an existing bag", description = "Modify an existing bag using its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Bag updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BagDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bag not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<BagDTO> update(@Valid @PathVariable Long id, @RequestBody BagDTO bagDTO) throws ResourceNotFoundException {
        bagDTO = bagService.update(id, bagDTO);
        return ResponseEntity.ok().body(bagDTO);
    }

    @Operation(summary = "Delete a bag", description = "Remove a bag from the system using its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Bag deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Bag not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        bagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}