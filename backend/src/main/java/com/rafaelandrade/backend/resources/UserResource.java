package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.UserDTO;
import com.rafaelandrade.backend.dto.UserDetailsResponseDTO;
import com.rafaelandrade.backend.dto.UserInsertDTO;
import com.rafaelandrade.backend.services.UserService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDetailsResponseDTO>> findAll(Pageable pageable){
        Page<UserDetailsResponseDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDetailsResponseDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        UserDetailsResponseDTO userDTO = service.findById(id);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<UserDetailsResponseDTO> findByUserName(@RequestParam String userName) throws ResourceNotFoundException {
        UserDetailsResponseDTO userDTO = userService.findByUserName(userName);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDetailsResponseDTO> insert(@Valid @RequestBody UserInsertDTO userDTO) throws ResourceNotFoundException {
        UserDetailsResponseDTO newDTO = service.insert(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDetailsResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) throws ResourceNotFoundException {
        UserDetailsResponseDTO newUserDTO = service.update(id, userDTO);
        return ResponseEntity.ok().body(newUserDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable  Long id) throws ResourceNotFoundException, DatabaseException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
