package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.OrderDTO;
import com.rafaelandrade.backend.dto.OrderUpdateDTO;
import com.rafaelandrade.backend.dto.RestaurantDTO;
import com.rafaelandrade.backend.services.OrderService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.EntityUpdateNotAllowedException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    ResponseEntity<Page<OrderDTO>> findAll(Pageable pageable){
        Page<OrderDTO> orders = orderService.findAll(pageable);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<OrderDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        OrderDTO orderDTO = orderService.findById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
        orderDTO = orderService.insert(orderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(orderDTO);
    }

    @PostMapping("/from-bag/{bagId}")
    public ResponseEntity<List<OrderDTO>> createOrdersFromBag(@PathVariable Long bagId, @RequestBody Map<Long, String> specialRequests) throws ResourceNotFoundException {
        List<OrderDTO> orders = orderService.insertFromBag(bagId, specialRequests);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bagId).toUri();
        return ResponseEntity.created(uri).body(orders);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> update(@Valid @PathVariable Long id, @RequestBody OrderUpdateDTO orderDTO) throws ResourceNotFoundException, EntityUpdateNotAllowedException {
        OrderDTO updatedOrder = orderService.update(id, orderDTO);
        return ResponseEntity.ok().body(updatedOrder);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
