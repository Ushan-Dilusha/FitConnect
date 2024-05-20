package com.paf.fitConnect.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import com.paf.fitConnect.modal.User;
import com.paf.fitConnect.modal.WorkoutPlan;
import com.paf.fitConnect.repository.UserRepository;
import com.paf.fitConnect.repository.WorkoutPlanRepository;
import com.paf.fitConnect.service.WorkoutPlanService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;

    @Override
    public List<WorkoutPlan> getAllWorkoutPlans() {
        try {
            return workoutPlanRepository.findAll();
        } catch (DataAccessException ex) {
            log.error("An error occurred while retrieving all workout plans: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Optional<WorkoutPlan> getWorkoutPlanById(String id) {
        try {
            return workoutPlanRepository.findById(id);
        } catch (DataAccessException ex) {
            log.error("An error occurred while retrieving workout plan with id {}: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public WorkoutPlan createWorkoutPlan(WorkoutPlan workoutPlan) {
        try {
            Optional<User> userOptional = userRepository.findById(workoutPlan.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                workoutPlan.setUserId(user.getId());
                workoutPlan.setUsername(user.getName());
                workoutPlan.setUserProfile(user.getProfileImage());
                return workoutPlanRepository.save(workoutPlan);
            } else {
                throw new IllegalArgumentException("User not found with ID: " + workoutPlan.getUserId());
            }
        } catch (DataAccessException | IllegalArgumentException ex) {
            log.error("An error occurred while creating workout plan: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public WorkoutPlan updateWorkoutPlan(String workoutPlanId, WorkoutPlan workoutPlan) {
        try {
            if (workoutPlanRepository.existsById(workoutPlanId)) {
                Optional<User> userOptional = userRepository.findById(workoutPlan.getUserId());
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    workoutPlan.setUserId(user.getId());
                    workoutPlan.setUsername(user.getName());
                    workoutPlan.setUserProfile(user.getProfileImage());
                    workoutPlan.setWorkoutPlanId(workoutPlanId);
                    return workoutPlanRepository.save(workoutPlan);
                } else {
                    throw new IllegalArgumentException("User not found with ID: " + workoutPlan.getUserId());
                }
            } else {
                throw new IllegalArgumentException("Workout plan not found with ID: " + workoutPlanId);
            }
        } catch (DataAccessException | IllegalArgumentException ex) {
            log.error("An error occurred while updating workout plan: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void deleteWorkoutPlan(String workoutPlanId) {
        try {
            workoutPlanRepository.deleteById(workoutPlanId);
        } catch (DataAccessException ex) {
            log.error("An error occurred while deleting workout plan with id {}: {}", workoutPlanId, ex.getMessage());
            throw ex;
        }
    }
}
