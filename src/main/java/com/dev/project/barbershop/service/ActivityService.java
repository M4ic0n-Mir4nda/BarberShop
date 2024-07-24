package com.dev.project.barbershop.service;

import com.dev.project.barbershop.payload.ActivityRequestPayload;
import com.dev.project.barbershop.exceptions.ErrorWhenSavingOrDeleteException;
import com.dev.project.barbershop.model.Activity;
import com.dev.project.barbershop.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity createService(ActivityRequestPayload payload) {
        Activity newActivity = new Activity(payload);
        this.activityRepository.save(newActivity);
        return newActivity;
    }

    public List<Activity> getAllActivities() {
        return this.activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(UUID activityId) { return this.activityRepository.findById(activityId); }

    public Activity updateActivity(Activity activity, ActivityRequestPayload payload) {
        try {
            String name = payload.name_service().trim();
            Double price = payload.price();
            LocalTime time = payload.time_service();
            String description = payload.description().trim();

            if (!name.equalsIgnoreCase("")) {
                activity.setNameService(name);
            }

            if (!price.equals(0d)) {
                activity.setPrice(price);
            }

            if (!time.equals(LocalTime.parse("00:00:00"))) {
                activity.setTimeService(time);
            }

            if (!description.equalsIgnoreCase("")) {
                activity.setDescription(description);
            }

            this.activityRepository.save(activity);

            return activity;
        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao atualizar serviço: ", err);
        }
    }

    public Boolean deleteActivity(UUID activityId) {
        try {
            this.activityRepository.deleteById(activityId);
            return true;
        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao deletar serviço: ", err);
        }
    }
}
