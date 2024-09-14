package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.OrderDTO;
import com.rafaelandrade.backend.dto.OrderDetailsResponseDTO;
import com.rafaelandrade.backend.dto.OrderUpdateDTO;
import com.rafaelandrade.backend.services.OrderService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.EntityUpdateNotAllowedException;
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
import java.util.List;
import java.util.Map;

@Tag(name = "Order", description = "Endpoints for managing orders.")
@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "List all orders", description = "Retrieve a paginated list of all orders.", responses = {
            @ApiResponse(responseCode = "200", description = "List of orders returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailsResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<OrderDetailsResponseDTO>> findAll(Pageable pageable) {
        Page<OrderDetailsResponseDTO> orders = orderService.findAll(pageable);
        return ResponseEntity.ok().body(orders);
    }

    @Operation(summary = "Find an order by ID", description = "Retrieve an order by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Order found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDetailsResponseDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        OrderDetailsResponseDTO orderDTO = orderService.findById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @Operation(summary = "Create a new order", description = "Add a new order to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Order created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<OrderDetailsResponseDTO> insert(@Valid @RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
        OrderDetailsResponseDTO orderResponse = orderService.insert(orderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(orderResponse);
    }

    @Operation(summary = "Create orders from bag", description = "Generate multiple orders from a bag with special requests.", responses = {
            @ApiResponse(responseCode = "201", description = "Orders created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bag not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping("/from-bag/{bagId}")
    public ResponseEntity<List<OrderDetailsResponseDTO>> createOrdersFromBag(@PathVariable Long bagId, @RequestBody Map<Long, String> specialRequests) throws ResourceNotFoundException {
        List<OrderDetailsResponseDTO> orders = orderService.insertFromBag(bagId, specialRequests);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bagId).toUri();
        return ResponseEntity.created(uri).body(orders);
    }

    @Operation(summary = "Updates the status of an existing order", description = "Modify the status of an existing order using its id.", responses = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "403", description = "Update not allowed.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntityUpdateNotAllowedException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderDetailsResponseDTO> update(@Valid @PathVariable Long id, @RequestBody OrderUpdateDTO orderDTO) throws ResourceNotFoundException, EntityUpdateNotAllowedException {
        OrderDetailsResponseDTO updatedOrder = orderService.update(id, orderDTO);
        return ResponseEntity.ok().body(updatedOrder);
    }
}
