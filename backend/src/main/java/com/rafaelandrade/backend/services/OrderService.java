package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.entities.common.OrderStatus;
import com.rafaelandrade.backend.dto.*;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.EntityUpdateNotAllowedException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BagRepository bagRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ItemRepository itemRepository, BagRepository bagRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.bagRepository = bagRepository;
    }

    @Transactional(readOnly = true)
    public Page<OrderDetailsResponseDTO> findAll(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(order -> new OrderDetailsResponseDTO(order, order.getItems()));
    }

    @Transactional(readOnly = true)
    public OrderDetailsResponseDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Order> orderObj = orderRepository.findById(id);
        Order orderEntity = orderObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        Set<Item> items = new HashSet<>();
        items.addAll(orderEntity.getItems());
        return new OrderDetailsResponseDTO(orderEntity, items);
    }

    @Transactional
    public OrderDetailsResponseDTO insert(OrderDTO orderDTO) throws ResourceNotFoundException {
        Order orderEntity = new Order();
        copyDtoToEntity(orderDTO, orderEntity);
        orderEntity.setCreatedAt(Instant.now());
        orderEntity = orderRepository.save(orderEntity);
        Set<Item> items = new HashSet<>();
        items.addAll(orderEntity.getItems());
        return new OrderDetailsResponseDTO(orderEntity, items);
    }

    @Transactional
    public List<OrderDetailsResponseDTO> insertFromBag(Long bagId, Map<Long, String> specialRequests) throws ResourceNotFoundException {
        Optional<Bag> bagObj = bagRepository.findById(bagId);
        bagObj.orElseThrow(() -> new ResourceNotFoundException("Bag with id " + bagId + " not found"));
        Bag bag = bagObj.get();

        List<Order> orders = new ArrayList<>();
        Map<LegalEntity, Set<Item>> itemsByRestaurant = new HashMap<>();

        for (Item item : bag.getItems()) {
            LegalEntity legalEntity = item.getMenu().getLegalEntity();
            itemsByRestaurant.computeIfAbsent(legalEntity, k -> new HashSet<>()).add(item);
        }

        for (Map.Entry<LegalEntity, Set<Item>> entry : itemsByRestaurant.entrySet()) {
            LegalEntity legalEntity = entry.getKey();
            Set<Item> items = entry.getValue();

            Order order = new Order();
            order.setLegalEntity(legalEntity);
            order.getItems().addAll(items);
            order.setClient(bag.getUser());
            order.setCreatedAt(Instant.now());
            order.setSpecialRequest(specialRequests.getOrDefault(legalEntity.getId(), ""));
            order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

            OrderDTO orderDTO = new OrderDTO(order, items);
            copyDtoToEntity(orderDTO, order);
            order = orderRepository.save(order);
            orders.add(order);
        }

        cleanBag(bag);
        bagRepository.save(bag);

        return orders.stream().map(order -> {
            Set<Item> items = new HashSet<>();
            items.addAll(order.getItems());
            return new OrderDetailsResponseDTO(order, items);
        }).collect(Collectors.toList());
    }

    @Transactional
    public OrderDetailsResponseDTO update(Long id, OrderUpdateDTO orderDTO) throws ResourceNotFoundException, EntityUpdateNotAllowedException {
        try {
            Order orderEntity = orderRepository.getReferenceById(id);

            if (orderEntity.getOrderStatus() != OrderStatus.CANCELED) {
                orderEntity.setOrderStatus(orderDTO.getOrderStatus());

                if (orderEntity.getOrderStatus() == OrderStatus.CANCELED) {
                    orderEntity.setCanceledAt(Instant.now());
                }

                orderEntity = orderRepository.save(orderEntity);
                Set<Item> items = new HashSet<>();
                items.addAll(orderEntity.getItems());
                return new OrderDetailsResponseDTO(orderEntity, items);
            }

            throw new EntityUpdateNotAllowedException("");

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        } catch (EntityUpdateNotAllowedException e) {
            throw new EntityUpdateNotAllowedException("Order with id " + id + " is canceled and cannot be updated");
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(OrderDTO orderDTO, Order orderEntity) throws ResourceNotFoundException {
        orderEntity.setSpecialRequest(orderDTO.getSpecialRequest());
        orderEntity.setDeliveryFee(orderDTO.getDeliveryFee());

        BigDecimal subTotal = BigDecimal.valueOf(0.00);
        Duration estimatedDeliveryTime = Duration.ZERO;

        Optional<User> userObj = userRepository.findById(orderDTO.getClient().getId());
        userObj.orElseThrow(() -> new ResourceNotFoundException("User with id " + orderDTO.getClient().getId() + " not found"));
        orderEntity.setClient(userObj.get());

        for (ItemDTO itemDTO : orderDTO.getItems()) {
            Optional<Item> itemObj = itemRepository.findById(itemDTO.getId());
            itemObj.orElseThrow(() -> new ResourceNotFoundException("Item with id " + itemDTO.getId() + " not found."));
            Item item = itemObj.get();
            orderEntity.getItems().add(item);

            if(item instanceof Dish){
                for(Additional additional : ((Dish) item).getAdditional()){
                    subTotal = subTotal.add(additional.getPrice());
                }
            }

            subTotal = subTotal.add(item.getCurrentPrice());
            estimatedDeliveryTime = estimatedDeliveryTime.plus(item.getPreparationTime());

            orderEntity.setLegalEntity(item.getMenu().getLegalEntity());
            orderEntity.setDeliveryFee(item.getMenu().getLegalEntity().getFixedDeliveryFee());
        }

        orderEntity.setSubTotal(subTotal);
        orderEntity.setTotalPrice(subTotal.add(orderEntity.getDeliveryFee()));
        orderEntity.setEstimatedDeliveryTime(estimatedDeliveryTime);
        orderEntity.setOrderStatus(OrderStatus.WAITING_PAYMENT);
    }

    private void cleanBag(Bag bag) {
        bag.getItems().clear();
        bag.setTotalPrice(BigDecimal.ZERO);
        bag.setDiscount(BigDecimal.ZERO);
        bag.setQuantityOfItems(0);
    }
}
