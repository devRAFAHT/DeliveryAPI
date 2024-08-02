package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.entities.common.OrderStatus;
import com.rafaelandrade.backend.dto.AssessmentDTO;
import com.rafaelandrade.backend.dto.AssessmentUpdateDTO;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.InvalidInputException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final DishRepository dishRepository;
    private final DrinkRepository drinkRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public AssessmentService(AssessmentRepository assessmentRepository, DishRepository dishRepository, DrinkRepository drinkRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.assessmentRepository = assessmentRepository;
        this.dishRepository = dishRepository;
        this.drinkRepository = drinkRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional(readOnly = true)
    public Page<AssessmentDTO> findAll(Pageable pageable) {
        Page<Assessment> assessments = assessmentRepository.findAll(pageable);
        return assessments.map(assessment -> new AssessmentDTO(assessment));
    }

    @Transactional(readOnly = true)
    public AssessmentDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Assessment> assessmentObj = assessmentRepository.findById(id);
        Assessment assessmentEntity = assessmentObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AssessmentDTO(assessmentEntity);
    }

    @Transactional
    public AssessmentDTO insert(AssessmentDTO assessmentDTO) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        Assessment assessmentEntity = new Assessment();
        copyDtoToEntity(assessmentDTO, assessmentEntity);
        assessmentEntity.setCreatedAt(Instant.now());
        assessmentEntity.getUpdateHistory().put(Instant.now(), "N/A");
        assessmentEntity = assessmentRepository.save(assessmentEntity);
        return new AssessmentDTO(assessmentEntity);
    }

    @Transactional
    public AssessmentDTO update(Long id, AssessmentUpdateDTO assessmentDTO) throws ResourceNotFoundException {
        try {
            Assessment assessmentEntity = assessmentRepository.getReferenceById(id);
            assessmentEntity.setUpdatedAt(Instant.now());

            Instant foundKey = null;
            for (Map.Entry<Instant, String> entry : assessmentEntity.getUpdateHistory().entrySet()) {
                if ("N/A".equals(entry.getValue())) {
                    foundKey = entry.getKey();
                    break;
                }
            }

            assessmentEntity.getUpdateHistory().put(foundKey, assessmentEntity.getComment());
            assessmentEntity.getUpdateHistory().put(Instant.now(), "N/A");
            assessmentEntity.setComment(assessmentDTO.getComment());
            assessmentEntity = assessmentRepository.save(assessmentEntity);
            return new AssessmentDTO(assessmentEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if(!assessmentRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            assessmentRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AssessmentDTO assessmentDTO, Assessment assessmentEntity) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        assessmentEntity.setId(assessmentDTO.getId());
        assessmentEntity.setComment(assessmentDTO.getComment());
        assessmentEntity.setCreatedAt(assessmentDTO.getCreatedAt());
        assessmentEntity.setUpdatedAt(assessmentDTO.getUpdatedAt());
        assessmentEntity.setPoints(assessmentDTO.getPoints());

        User user = userRepository.findById(assessmentDTO.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + assessmentDTO.getUser().getId() + " not found."));
        assessmentEntity.setUser(user);

        if(assessmentDTO.getDish() != null && assessmentDTO.getDrink() != null){
            throw new InvalidInputException("Both dish and drink specified in DTO, but only one should be provided.");
        }else if(assessmentDTO.getDish() == null && assessmentDTO.getDrink() == null) {
            throw new InvalidInputException("Neither dish nor drink specified in DTO.");
        }

        if (assessmentDTO.getDrink() != null) {
            Drink drink = drinkRepository.findById(assessmentDTO.getDrink().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Drink with id " + assessmentDTO.getDrink().getId() + " not found."));
            assessmentEntity.setDrink(drink);

            Restaurant restaurant = restaurantRepository.findById(drink.getMenu().getRestaurant().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant with id " + drink.getMenu().getRestaurant().getId() + " not found."));
            assessmentEntity.setRestaurant(restaurant);

        } else if (assessmentDTO.getDish() != null) {
            Dish dish = dishRepository.findById(assessmentDTO.getDish().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dish with id " + assessmentDTO.getDish().getId() + " not found."));
            assessmentEntity.setDish(dish);

            Restaurant restaurant = restaurantRepository.findById(dish.getMenu().getRestaurant().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant with id " + dish.getMenu().getRestaurant().getId() + " not found."));
            assessmentEntity.setRestaurant(restaurant);
        }

        for(Order order : user.getOrderHitory()){
            if(!order.getDrinks().contains(assessmentEntity.getDrink()) || !order.getDishes().contains(assessmentEntity.getDish())){
                if(order.getOrderStatus() != OrderStatus.ORDER_DELIVERED){
                    throw new InvalidInputException("The item has not been consumed by the user and cannot be evaluated.");
                }
            }
        }
    }
}