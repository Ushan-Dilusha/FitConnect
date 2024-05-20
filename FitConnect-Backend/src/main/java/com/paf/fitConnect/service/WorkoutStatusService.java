package com.paf.fitConnect.service;

import com.paf.fitConnect.modal.WorkoutStatus;

import java.util.List;
import java.util.Optional;

public interface WorkoutStatusService {

    List<WorkoutStatus> getAllWorkoutStatus();

    Optional<WorkoutStatus> getWorkoutStatsusById(String statusId);

    WorkoutStatus createWorkoutStatus(WorkoutStatus workoutStatus);

    WorkoutStatus updateWorkoutStatus(String statusId, WorkoutStatus workoutStatus);

    void deleteWorkoutStatus(String statusId);
}