package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AssessmentResponseDTO;
import com.rafaelandrade.backend.services.AssessmentResponseService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.InvalidInputException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/assessmentsResponses")
public class AssessmentResponseResource {

    private final AssessmentResponseService assessmentResponseService;

    public AssessmentResponseResource(AssessmentResponseService assessmentResponseService) {
        this.assessmentResponseService = assessmentResponseService;
    }

    @GetMapping
    ResponseEntity<Page<AssessmentResponseDTO>> findAll(Pageable pageable){
        Page<AssessmentResponseDTO> assessmentsResponses = assessmentResponseService.findAll(pageable);
        return ResponseEntity.ok().body(assessmentsResponses);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<AssessmentResponseDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AssessmentResponseDTO assessmentResponseDTO = assessmentResponseService.findById(id);
        return ResponseEntity.ok().body(assessmentResponseDTO);
    }

    @PostMapping
    public ResponseEntity<AssessmentResponseDTO> insert(@Valid @RequestBody AssessmentResponseDTO assessmentResponseDTO) throws ResourceNotFoundException, InvalidInputException {
        assessmentResponseDTO = assessmentResponseService.insert(assessmentResponseDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(assessmentResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(assessmentResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AssessmentResponseDTO> update(@Valid @PathVariable Long id, @RequestBody AssessmentResponseDTO assessmentResponseDTO) throws ResourceNotFoundException, InvalidInputException {
        assessmentResponseDTO = assessmentResponseService.update(id, assessmentResponseDTO);
        return ResponseEntity.ok().body(assessmentResponseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AssessmentResponseDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        assessmentResponseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
