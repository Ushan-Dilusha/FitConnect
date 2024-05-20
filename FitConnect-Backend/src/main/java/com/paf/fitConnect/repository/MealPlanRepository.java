package com.paf.fitConnect.repository;

import com.paf.fitConnect.modal.MealPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealPlanRepository extends MongoRepository<MealPlan, String> {

}
