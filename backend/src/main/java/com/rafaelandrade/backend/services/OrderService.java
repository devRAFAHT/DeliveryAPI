package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.entities.common.OrderStatus;
import com.rafaelandrade.backend.dto.*;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.EntityUpdateNotAllowedException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
        logger.info("Finding all orders with pagination: {}", pageable);
        Page<Order> orders = orderRepository.findAll(pageable);
        logger.info("Found {} orders", orders.getTotalElements());
        return orders.map(order -> {
            logger.info("Mapping order id {} to DTO", order.getId());
            return new OrderDetailsResponseDTO(order, order.getItems());
        });
    }

    @Transactional(readOnly = true)
    public OrderDetailsResponseDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding order by id: {}", id);
        Optional<Order> orderObj = orderRepository.findById(id);
        Order orderEntity = orderObj.orElseThrow(() -> {
            logger.error("Order not found with id: {}", id);
            return new ResourceNotFoundException("Entity not found");
        });
        logger.info("Order found: {}", orderEntity);
        Set<Item> items = new HashSet<>(orderEntity.getItems());
        return new OrderDetailsResponseDTO(orderEntity, items);
    }

    @Transactional
    public OrderDetailsResponseDTO insert(OrderDTO orderDTO) throws ResourceNotFoundException {
        logger.info("Inserting new order: {}", orderDTO);
        Order orderEntity = new Order();
        checkIfAssociatedEntitiesExist(orderDTO);
        copyDtoToEntity(orderDTO, orderEntity);
        orderEntity.setCreatedAt(Instant.now());
        orderEntity = orderRepository.save(orderEntity);
        logger.info("Order inserted with id: {}", orderEntity.getId());
        Set<Item> items = new HashSet<>(orderEntity.getItems());
        return new OrderDetailsResponseDTO(orderEntity, items);
    }

    @Transactional
    public List<OrderDetailsResponseDTO> insertFromBag(Long bagId, Map<Long, String> specialRequests) throws ResourceNotFoundException {
        logger.info("Inserting orders from bag with id: {}", bagId);
        Bag bag = bagRepository.getReferenceById(bagId);

        if (bag == null) {
            logger.error("Bag with id {} not found", bagId);
            throw new ResourceNotFoundException("Bag with id " + bagId + " not found");
        }

        List<Order> orders = new ArrayList<>();
        Map<LegalEntity, Set<Item>> itemsByRestaurant = new HashMap<>();

        for (Item item : bag.getItems()) {
            LegalEntity legalEntity = item.getMenu().getLegalEntity();
            itemsByRestaurant.computeIfAbsent(legalEntity, k -> new HashSet<>()).add(item);
        }

        for (Map.Entry<LegalEntity, Set<Item>> entry : itemsByRestaurant.entrySet()) {
            LegalEntity legalEntity = entry.getKey();
            Set<Item> items = entry.getValue();

            logger.info("Creating order for legal entity: {}", legalEntity.getId());
            Order order = new Order();
            order.setLegalEntity(legalEntity);
            order.getItems().addAll(items);
            order.setClient(bag.getUser());
            order.setCreatedAt(Instant.now());
            order.setSpecialRequest(specialRequests.getOrDefault(legalEntity.getId(), ""));
            order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

            OrderDTO orderDTO = new OrderDTO(order, items);
            checkIfAssociatedEntitiesExist(orderDTO);
            copyDtoToEntity(orderDTO, order);
            order = orderRepository.save(order);
            logger.info("Order created with id: {}", order.getId());
            orders.add(order);
        }

        cleanBag(bag);
        bagRepository.save(bag);
        logger.info("Bag cleaned and saved");

        return orders.stream().map(order -> {
            Set<Item> items = new HashSet<>(order.getItems());
            return new OrderDetailsResponseDTO(order, items);
        }).collect(Collectors.toList());
    }

    @Transactional
    public OrderDetailsResponseDTO update(Long id, OrderUpdateDTO orderDTO) throws ResourceNotFoundException, EntityUpdateNotAllowedException {
        logger.info("Updating order with id: {} using {}", id, orderDTO);
        try {
            Order orderEntity = orderRepository.getReferenceById(id);

            if (orderEntity.getOrderStatus() != OrderStatus.CANCELED) {
                orderEntity.setOrderStatus(orderDTO.getOrderStatus());

                if (orderEntity.getOrderStatus() == OrderStatus.CANCELED) {
                    orderEntity.setCanceledAt(Instant.now());
                }

                orderEntity = orderRepository.save(orderEntity);
                logger.info("Order updated with id: {}", orderEntity.getId());
                Set<Item> items = new HashSet<>(orderEntity.getItems());
                return new OrderDetailsResponseDTO(orderEntity, items);
            }

            throw new EntityUpdateNotAllowedException("Order with id " + id + " is canceled and cannot be updated");

        } catch (EntityNotFoundException e) {
            logger.error("Order not found with id: {}", id, e);
            throw new ResourceNotFoundException("Id not found: " + id);
        } catch (EntityUpdateNotAllowedException e) {
            logger.error("Order with id {} is canceled and cannot be updated", id, e);
            throw new EntityUpdateNotAllowedException("Order with id " + id + " is canceled and cannot be updated");
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting order with id: {}", id);
        if (!orderRepository.existsById(id)) {
            logger.error("Order with id {} not found", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            orderRepository.deleteById(id);
            logger.info("Order deleted with id: {}", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting order with id: {}", id, e);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(OrderDTO orderDTO, Order orderEntity) throws ResourceNotFoundException {
        logger.info("Copying DTO to entity: {}", orderDTO);
        orderEntity.setSpecialRequest(orderDTO.getSpecialRequest());
        orderEntity.setDeliveryFee(orderDTO.getDeliveryFee());

        BigDecimal subTotal = BigDecimal.ZERO;
        Duration estimatedDeliveryTime = Duration.ZERO;

        User user = userRepository.getReferenceById(orderDTO.getClient().getId());
        orderEntity.setClient(user);

        for (ItemDTO itemDTO : orderDTO.getItems()) {
            Item item = itemRepository.getReferenceById(itemDTO.getId());
            orderEntity.getItems().add(item);

            if (item instanceof Dish) {
                for (Additional additional : ((Dish) item).getAdditional()) {
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
        logger.info("Order entity updated with subTotal: {}, totalPrice: {}, estimatedDeliveryTime: {}",
                subTotal, orderEntity.getTotalPrice(), estimatedDeliveryTime);
    }

    private void cleanBag(Bag bag) {
        logger.info("Cleaning bag: {}", bag);
        bag.getItems().clear();
        bag.setTotalPrice(BigDecimal.ZERO);
        bag.setDiscount(BigDecimal.ZERO);
        bag.setQuantityOfItems(0);
        logger.info("Bag cleaned: {}", bag);
    }

    private void checkIfAssociatedEntitiesExist(OrderDTO orderDTO) throws ResourceNotFoundException {
        logger.info("Checking associated entities for orderDTO: {}", orderDTO);
        if (!bagRepository.existsById(orderDTO.getClient().getBag().getId())) {
            logger.error("Bag with id {} not found", orderDTO.getClient().getBag().getId());
            throw new ResourceNotFoundException("Bag with id " + orderDTO.getClient().getBag().getId() + " not found");
        }

        for (ItemDTO itemDTO : orderDTO.getItems()) {
            if (!itemRepository.existsById(itemDTO.getId())) {
                logger.error("Item with id {} not found", itemDTO.getId());
                throw new ResourceNotFoundException("Item with id " + itemDTO.getId() + " not found");
            }
        }

        logger.info("All associated entities found");
    }
}