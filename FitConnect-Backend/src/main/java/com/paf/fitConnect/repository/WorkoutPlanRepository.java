package com.paf.fitConnect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.paf.fitConnect.modal.WorkoutPlan;

@Repository
public interface WorkoutPlanRepository extends MongoRepository<WorkoutPlan, String> {

}