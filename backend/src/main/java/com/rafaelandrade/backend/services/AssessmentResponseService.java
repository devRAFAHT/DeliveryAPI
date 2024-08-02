package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AssessmentResponseDTO;
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
import java.util.Optional;

@Service
public class AssessmentResponseService {

    private final AssessmentResponseRepository assessmentResponseRepository;

    private final RestaurantRepository restaurantRepository;

    private final AssessmentRepository assessmentRepository;

    public AssessmentResponseService(AssessmentResponseRepository assessmentResponseRepository, RestaurantRepository restaurantRepository, AssessmentRepository assessmentRepository) {
        this.assessmentResponseRepository = assessmentResponseRepository;
        this.restaurantRepository = restaurantRepository;
        this.assessmentRepository = assessmentRepository;
    }


    @Transactional(readOnly = true)
    public Page<AssessmentResponseDTO> findAll(Pageable pageable) {
        Page<AssessmentResponse> assessmentsResponses = assessmentResponseRepository.findAll(pageable);
        return assessmentsResponses.map(assessmentResponse -> new AssessmentResponseDTO(assessmentResponse));
    }

    @Transactional(readOnly = true)
    public AssessmentResponseDTO findById(Long id) throws ResourceNotFoundException {
        Optional<AssessmentResponse> assessmentResponseObj = assessmentResponseRepository.findById(id);
        AssessmentResponse assessmentResponseEntity = assessmentResponseObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AssessmentResponseDTO(assessmentResponseEntity);
    }

    @Transactional
    public AssessmentResponseDTO insert(AssessmentResponseDTO assessmentResponseDTO) throws ResourceNotFoundException, InvalidInputException {
        AssessmentResponse assessmentResponseEntity = new AssessmentResponse();
        copyDtoToEntity(assessmentResponseDTO, assessmentResponseEntity);
        assessmentResponseEntity.setCreatedAt(Instant.now());
        assessmentResponseEntity = assessmentResponseRepository.save(assessmentResponseEntity);
        return new AssessmentResponseDTO(assessmentResponseEntity);
    }

    @Transactional
    public AssessmentResponseDTO update(Long id, AssessmentResponseDTO assessmentResponseDTO) throws ResourceNotFoundException, InvalidInputException {
        try {
            AssessmentResponse assessmentResponseEntity = assessmentResponseRepository.getReferenceById(id);
            copyDtoToEntity(assessmentResponseDTO, assessmentResponseEntity);
            assessmentResponseEntity = assessmentResponseRepository.save(assessmentResponseEntity);
            return new AssessmentResponseDTO(assessmentResponseEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if(!assessmentResponseRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            assessmentResponseRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AssessmentResponseDTO assessmentResponseDTO, AssessmentResponse assessmentResponseEntity) throws ResourceNotFoundException, InvalidInputException {
        assessmentResponseEntity.setComment(assessmentResponseDTO.getComment());

        Assessment assessment = assessmentRepository.findById(assessmentResponseDTO.getAssessment().getId()).orElseThrow(() -> new ResourceNotFoundException("Assessment with id " + assessmentResponseDTO.getId() + " not found."));
        assessmentResponseEntity.setAssessment(assessment);

        Restaurant restaurant = restaurantRepository.findById(assessmentResponseDTO.getRestaurant().getId()).orElseThrow(() -> new ResourceNotFoundException("Restaurant with id " + assessmentResponseDTO.getRestaurant().getId() + " not found."));

        for(Menu menu : restaurant.getMenus()){
            if(menu.getDrinks().contains(assessment.getDrink()) || menu.getDishes().contains(assessment.getDish())){
                assessmentResponseEntity.setRestaurant(restaurant);
                break;
            }else{
                throw new InvalidInputException("A restaurant cannot respond to an evaluation of another restaurant.");
            }
        }
    }
}