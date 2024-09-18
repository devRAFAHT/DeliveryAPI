package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AssessmentResponseDTO;
import com.rafaelandrade.backend.dto.AssessmentResponseUpdateDTO;
import com.rafaelandrade.backend.services.AssessmentResponseService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.InvalidInputException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "AssessmentResponse", description = "Endpoints for managing assessment responses.")
@RestController
@RequestMapping(value = "/assessmentsResponses")
public class AssessmentResponseResource {

    private final AssessmentResponseService assessmentResponseService;

    public AssessmentResponseResource(AssessmentResponseService assessmentResponseService) {
        this.assessmentResponseService = assessmentResponseService;
    }

    @Operation(summary = "List all assessment responses", description = "Resource to list all assessment responses.", responses = {
            @ApiResponse(responseCode = "200", description = "List of assessment responses returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessmentResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<AssessmentResponseDTO>> findAll(Pageable pageable) {
        Page<AssessmentResponseDTO> assessmentsResponses = assessmentResponseService.findAll(pageable);
        return ResponseEntity.ok().body(assessmentsResponses);
    }

    @Operation(summary = "Search for an assessment response by ID", description = "Resource to search for an assessment response by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Assessment response found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessmentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assessment response not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AssessmentResponseDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AssessmentResponseDTO assessmentResponseDTO = assessmentResponseService.findById(id);
        return ResponseEntity.ok().body(assessmentResponseDTO);
    }

    @Operation(summary = "Create a new assessment response", description = "Resource to create a new assessment response.", responses = {
            @ApiResponse(responseCode = "201", description = "Assessment response created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessmentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidInputException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @PostMapping
    public ResponseEntity<AssessmentResponseDTO> insert(@Valid @RequestBody AssessmentResponseDTO assessmentResponseDTO)
            throws ResourceNotFoundException, InvalidInputException {
        assessmentResponseDTO = assessmentResponseService.insert(assessmentResponseDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(assessmentResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(assessmentResponseDTO);
    }

    @Operation(summary = "Update the comment of an assessment response", description = "Resource to update the comment of an existing Assessment Response.", responses = {
            @ApiResponse(responseCode = "200", description = "Assessment response updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessmentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assessment response not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidInputException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<AssessmentResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AssessmentResponseUpdateDTO assessmentResponseUpdateDTO)
            throws ResourceNotFoundException, InvalidInputException {
        AssessmentResponseDTO assessmentResponseDTO = assessmentResponseService.update(id, assessmentResponseUpdateDTO);
        return ResponseEntity.ok().body(assessmentResponseDTO);
    }

    @Operation(summary = "Delete an assessment response", description = "Delete an assessment response by its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Assessment response deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Assessment response not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        assessmentResponseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}