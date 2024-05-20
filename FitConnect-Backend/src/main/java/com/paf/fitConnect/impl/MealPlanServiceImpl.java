package com.paf.fitConnect.impl;

import com.paf.fitConnect.modal.User;
import com.paf.fitConnect.repository.MealPlanRepository;
import com.paf.fitConnect.repository.UserRepository;
import com.paf.fitConnect.modal.MealPlan;
import com.paf.fitConnect.service.MealPlanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MealPlanServiceImpl implements MealPlanService {

    private MealPlanRepository mealPlanRepository;
    private UserRepository userRepository;

    @Override
    public List<MealPlan> getAllMealPlans() {
        return mealPlanRepository.findAll();
    }

    @Override
    public Optional<MealPlan> getMealPlanById(String mealPlanId) {
        return mealPlanRepository.findById(mealPlanId);
    }

    @Override
    public MealPlan createMealPlan(MealPlan mealPlan) {
        Optional<User> userOptional = userRepository.findById(mealPlan.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            mealPlan.setUserId(user.getId());
            mealPlan.setUsername(user.getName());
            mealPlan.setUserProfile(user.getProfileImage());
            return mealPlanRepository.save(mealPlan);
        } else {
            return null;
        }
    }

    @Override
    public MealPlan updatMealPlan(String mealPlanId, MealPlan mealPlan) {
        if (mealPlanRepository.existsById(mealPlanId)) {
            Optional<User> userOptional = userRepository.findById(mealPlan.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                mealPlan.setUserId(user.getId());
                mealPlan.setUsername(user.getName());
                mealPlan.setUserProfile(user.getProfileImage());
                mealPlan.setMealPlanId(mealPlanId);
                mealPlan.setMealType(mealPlan.getMealType());
                mealPlan.setRecipes(mealPlan.getRecipes());
                mealPlan.setIngredients(mealPlan.getIngredients());
                mealPlan.setCookingInstruction(mealPlan.getCookingInstruction());
                mealPlan.setNutritionalInformation(mealPlan.getNutritionalInformation());
                mealPlan.setPortionSizes(mealPlan.getPortionSizes());
                mealPlan.setDietaryPreferences(mealPlan.getDietaryPreferences());
                mealPlan.setSource(mealPlan.getSource());
                mealPlan.setDate(mealPlan.getDate());
                return mealPlanRepository.save(mealPlan);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void deleteMealPlan(String mealPlanId) {
        mealPlanRepository.deleteById(mealPlanId);
    }

}
