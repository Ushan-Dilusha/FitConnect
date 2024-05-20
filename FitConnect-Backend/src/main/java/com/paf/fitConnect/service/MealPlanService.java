package com.paf.fitConnect.service;

import com.paf.fitConnect.modal.MealPlan;

import java.util.List;
import java.util.Optional;

public interface MealPlanService {

    List<MealPlan> getAllMealPlans();

    Optional<MealPlan> getMealPlanById(String mealPlanId);

    MealPlan createMealPlan(MealPlan mealPlan);

    MealPlan updatMealPlan(String mealPlanId, MealPlan mealPlan);

    void deleteMealPlan(String mealPlanId);

}
