package com.paf.fitConnect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.paf.fitConnect.modal.WorkoutStatus;

@Repository
public interface WorkoutStatusRepository extends MongoRepository<WorkoutStatus, String> {

}