package com.paf.fitConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paf.fitConnect.modal.WorkoutStatus;
import com.paf.fitConnect.service.WorkoutStatusService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/workoutStatus")
public class WorkoutStatusController {

    private final WorkoutStatusService workoutStatusService;

    @GetMapping
    public List<WorkoutStatus> getAllWorkoutStatus() {
        return workoutStatusService.getAllWorkoutStatus();
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<WorkoutStatus> getWorkoutstatusById(@PathVariable String statusId) {
        Optional<WorkoutStatus> workoutStatus = workoutStatusService.getWorkoutStatsusById(statusId);
        return workoutStatus.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<WorkoutStatus> createWorkoutStatus(@RequestBody WorkoutStatus workoutStatus) {
        WorkoutStatus savedWorkoutStatus = workoutStatusService.createWorkoutStatus(workoutStatus);
        return new ResponseEntity<>(savedWorkoutStatus, HttpStatus.CREATED);
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<WorkoutStatus> updateWorkoutStatus(@PathVariable String statusId,
            @RequestBody WorkoutStatus workoutStatus) {
        WorkoutStatus updateWorkoutStatus = workoutStatusService.updateWorkoutStatus(statusId, workoutStatus);
        if (updateWorkoutStatus != null) {
            return new ResponseEntity<>(updateWorkoutStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<Void> deleteWorkoutStatus(@PathVariable String statusId) {
        workoutStatusService.deleteWorkoutStatus(statusId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}