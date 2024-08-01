package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.common.OrderStatus;
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

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    private final DrinkRepository drinkRepository;

    private final BagRepository bagRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository, DrinkRepository drinkRepository, BagRepository bagRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.drinkRepository = drinkRepository;
        this.bagRepository = bagRepository;
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(order -> new OrderDTO(order, order.getDishes(), order.getDrinks()));
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Order> orderObj = orderRepository.findById(id);
        Order orderEntity = orderObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new OrderDTO(orderEntity, orderEntity.getDishes(), orderEntity.getDrinks());
    }

    @Transactional
    public OrderDTO insert(OrderDTO orderDTO) throws ResourceNotFoundException {
        Order orderEntity = new Order();
        copyDtoToEntity(orderDTO, orderEntity);
        orderEntity.setCreatedAt(Instant.now());
        orderEntity = orderRepository.save(orderEntity);
        return new OrderDTO(orderEntity, orderEntity.getDishes(), orderEntity.getDrinks());
    }

    @Transactional
    public List<OrderDTO> insertFromBag(Long bagId, Map<Long, String> specialRequests) throws ResourceNotFoundException {
        Optional<Bag> bagObj = bagRepository.findById(bagId);
        bagObj.orElseThrow(() -> new ResourceNotFoundException("Bag with id " + bagId + " not found"));
        Bag bag = bagObj.get();

        List<Order> orders = new ArrayList<>();

        Map<Restaurant, List<Dish>> dishesByRestaurant = new HashMap<>();
        Map<Restaurant, List<Drink>> drinksByRestaurant = new HashMap<>();

        for (Dish dish : bag.getDishes()) {
            Restaurant restaurant = dish.getMenu().getRestaurant();
            dishesByRestaurant.computeIfAbsent(restaurant, k -> new ArrayList<>()).add(dish);
        }

        for (Drink drink : bag.getDrinks()) {
            Restaurant restaurant = drink.getMenu().getRestaurant();
            drinksByRestaurant.computeIfAbsent(restaurant, k -> new ArrayList<>()).add(drink);
        }

        for(Restaurant restaurant : dishesByRestaurant.keySet()){
            Order order = new Order();
            order.setRestaurant(restaurant);
            order.getDishes().addAll(dishesByRestaurant.get(restaurant));
            order.getDrinks().addAll(drinksByRestaurant.getOrDefault(restaurant, new ArrayList<>()));
            order.setClient(bag.getUser());
            order.setCreatedAt(Instant.now());
            order.setSpecialRequest(specialRequests.getOrDefault(restaurant.getId(), ""));

            OrderDTO orderDTO = new OrderDTO(order, order.getDishes(), order.getDrinks());
            copyDtoToEntity(orderDTO, order);
            order = orderRepository.save(order);
            orders.add(order);
        }

        for(Restaurant restaurant : drinksByRestaurant.keySet()){
            if(!dishesByRestaurant.containsKey(restaurant)){
                Order order = new Order();
                order.setRestaurant(restaurant);
                order.getDrinks().addAll(drinksByRestaurant.get(restaurant));
                order.setClient(bag.getUser());
                order.setSpecialRequest(specialRequests.getOrDefault(restaurant.getId(), ""));
                order.setCreatedAt(Instant.now());

                OrderDTO orderDTO = new OrderDTO(order, order.getDishes(), order.getDrinks());
                copyDtoToEntity(orderDTO, order);
                order = orderRepository.save(order);
                orders.add(order);
            }
        }

        cleanBag(bag);
        bagRepository.save(bag);

        return orders.stream().map(order -> new OrderDTO(order, order.getDishes(), order.getDrinks())).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO update(Long id, OrderUpdateDTO orderDTO) throws ResourceNotFoundException, EntityUpdateNotAllowedException {
        try {
            Order orderEntity = orderRepository.getReferenceById(id);

            if(orderEntity.getOrderStatus() != OrderStatus.CANCELED) {
                orderEntity.setOrderStatus(orderDTO.getOrderStatus());

                if (orderEntity.getOrderStatus() == OrderStatus.CANCELED) {
                    orderEntity.setCanceledAt(Instant.now());
                }

                orderEntity = orderRepository.save(orderEntity);
                return new OrderDTO(orderEntity, orderEntity.getDishes(), orderEntity.getDrinks());
            }

            throw new EntityUpdateNotAllowedException("");

        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        } catch (EntityUpdateNotAllowedException e) {
            throw new EntityUpdateNotAllowedException("Order with id " + id + " is canceled and cannot be updated");
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if(!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            orderRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
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

        for(DishDTO dishDTO : orderDTO.getDishes()){
            Optional<Dish> dishObj = dishRepository.findById(dishDTO.getId());
            dishObj.orElseThrow(() -> new ResourceNotFoundException("Dish with id " + dishDTO.getId() + " not found."));
            Dish dish = dishObj.get();
            orderEntity.getDishes().add(dish);
            subTotal = subTotal.add(dish.getCurrentPrice());
            estimatedDeliveryTime = estimatedDeliveryTime.plus(dish.getPreparationTime());

            orderEntity.setRestaurant(dish.getMenu().getRestaurant());
            orderEntity.setDeliveryFee(dish.getMenu().getRestaurant().getFixedDeliveryFee());
        }

        for(DrinkDTO drinkDTO : orderDTO.getDrinks()){
            Optional<Drink> drinkObj = drinkRepository.findById(drinkDTO.getId());
            drinkObj.orElseThrow(() -> new ResourceNotFoundException("Drink with id " + drinkDTO.getId() + " not found."));
            Drink drink = drinkObj.get();
            orderEntity.getDrinks().add(drink);
            subTotal = subTotal.add(drink.getCurrentPrice());
            orderEntity.setDeliveryFee(drink.getMenu().getRestaurant().getFixedDeliveryFee());
        }

        orderEntity.setSubTotal(subTotal);
        orderEntity.setTotalPrice(subTotal.add(orderEntity.getDeliveryFee()));
        orderEntity.setEstimatedDeliveryTime(estimatedDeliveryTime);
        orderEntity.setOrderStatus(OrderStatus.WAITING_PAYMENT);
    }

    private void cleanBag(Bag bag){
        bag.getDishes().clear();
        bag.getDrinks().clear();
        bag.setTotalPrice(BigDecimal.ZERO);
        bag.setDiscount(BigDecimal.ZERO);
        bag.setQuantityOfItems(0);
    }
}