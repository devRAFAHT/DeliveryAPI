package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.BagDTO;
import com.rafaelandrade.backend.services.BagService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/bags")
public class BagResource {

    private final BagService bagService;

    public BagResource(BagService bagService) {
        this.bagService = bagService;
    }

    @GetMapping
    ResponseEntity<Page<BagDTO>> findAll(Pageable pageable){
        Page<BagDTO> bags = bagService.findAll(pageable);
        return ResponseEntity.ok().body(bags);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<BagDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        BagDTO bagDTO = bagService.findById(id);
        return ResponseEntity.ok().body(bagDTO);
    }

    @PostMapping
    public ResponseEntity<BagDTO> insert(@Valid @RequestBody BagDTO bagDTO) throws ResourceNotFoundException {
        bagDTO = bagService.insert(bagDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bagDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(bagDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BagDTO> update(@Valid @PathVariable Long id, @RequestBody BagDTO bagDTO) throws ResourceNotFoundException {
        bagDTO = bagService.update(id, bagDTO);
        return ResponseEntity.ok().body(bagDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BagDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        bagService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
