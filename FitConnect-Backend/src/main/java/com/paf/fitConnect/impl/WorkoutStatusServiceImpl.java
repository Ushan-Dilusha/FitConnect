package com.paf.fitConnect.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import com.paf.fitConnect.modal.User;
import com.paf.fitConnect.modal.WorkoutStatus;
import com.paf.fitConnect.repository.UserRepository;
import com.paf.fitConnect.repository.WorkoutStatusRepository;
import com.paf.fitConnect.service.WorkoutStatusService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class WorkoutStatusServiceImpl implements WorkoutStatusService {

    private final WorkoutStatusRepository workoutStatusRepository;
    private final UserRepository userRepository;

    @Override
    public List<WorkoutStatus> getAllWorkoutStatus() {
        try {
            return workoutStatusRepository.findAll();
        } catch (DataAccessException ex) {
            log.error("An error occurred while retrieving all workout statuses: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Optional<WorkoutStatus> getWorkoutStatsusById(String statusId) {
        try {
            return workoutStatusRepository.findById(statusId);
        } catch (DataAccessException ex) {
            log.error("An error occurred while retrieving workout status with id {}: {}", statusId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public WorkoutStatus createWorkoutStatus(WorkoutStatus workoutStatus) {
        try {
            Optional<User> userOptional = userRepository.findById(workoutStatus.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                workoutStatus.setUserId(user.getId());
                workoutStatus.setUsername(user.getName());
                workoutStatus.setUserProfile(user.getProfileImage());
                return workoutStatusRepository.save(workoutStatus);
            } else {
                throw new IllegalArgumentException("User not found with ID: " + workoutStatus.getUserId());
            }
        } catch (DataAccessException | IllegalArgumentException ex) {
            log.error("An error occurred while creating workout status: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public WorkoutStatus updateWorkoutStatus(String statusId, WorkoutStatus workoutStatus) {
        try {
            if (workoutStatusRepository.existsById(statusId)) {
                Optional<User> userOptional = userRepository.findById(workoutStatus.getUserId());
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    workoutStatus.setUserId(user.getId());
                    workoutStatus.setUsername(user.getName());
                    workoutStatus.setUserProfile(user.getProfileImage());
                    workoutStatus.setStatusId(statusId);
                    return workoutStatusRepository.save(workoutStatus);
                } else {
                    throw new IllegalArgumentException("User not found with ID: " + workoutStatus.getUserId());
                }
            } else {
                throw new IllegalArgumentException("Workout status not found with ID: " + statusId);
            }
        } catch (DataAccessException | IllegalArgumentException ex) {
            log.error("An error occurred while updating workout status: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void deleteWorkoutStatus(String statusId) {
        try {
            workoutStatusRepository.deleteById(statusId);
        } catch (DataAccessException ex) {
            log.error("An error occurred while deleting workout status with id {}: {}", statusId, ex.getMessage());
            throw ex;
        }
    }
}
