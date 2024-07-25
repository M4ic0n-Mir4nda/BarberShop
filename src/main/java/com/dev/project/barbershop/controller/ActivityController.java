package com.dev.project.barbershop.controller;

import com.dev.project.barbershop.model.Activity;
import com.dev.project.barbershop.repository.ActivityRepository;
import com.dev.project.barbershop.payload.ActivityRequestPayload;
import com.dev.project.barbershop.service.ActivityService;
import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.NotFoundRecordsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<Activity> createService(@RequestBody ActivityRequestPayload payload) {
        Activity newActivity = this.activityService.createService(payload);

        return ResponseEntity.ok(newActivity);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getAllActivities() throws NotFoundRecordsException {
        List<Activity> activitiesList = this.activityService.getAllActivities();

        if (activitiesList.isEmpty()) {
            throw new NotFoundRecordsException("Nenhum serviço cadastrado");
        }

        return ResponseEntity.ok(activitiesList);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<Activity> getActivityById(@PathVariable UUID activityId) throws CustomException {
        Optional<Activity> activity = this.activityService.getActivityById(activityId);

        if (activity.isEmpty()) {
            throw new CustomException("Serviço não encontrado, tente novamente!");
        }

        return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{activityId}/update")
    public ResponseEntity<Activity> updateActivity(@PathVariable UUID activityId, @RequestBody ActivityRequestPayload payload) throws CustomException {
        Optional<Activity> activity = this.activityService.getActivityById(activityId);

        if (activity.isPresent()) {
            Activity rawActivity = activity.get();

            Activity updateActivity = this.activityService.updateActivity(rawActivity, payload);

            return ResponseEntity.ok(updateActivity);
        }

        throw new CustomException("Serviço não encontrado, tente novamente!");
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Map<String, String>> deleteActivity(@PathVariable UUID activityId) throws CustomException {
        Optional<Activity> activityDelete = this.activityService.getActivityById(activityId);

        if (activityDelete.isPresent()) {
            Boolean deleteReturn = this.activityService.deleteActivity(activityId);
            if (deleteReturn) {
                Map<String, String> message = new HashMap<>();
                message.put("message", "Serviço deletado com sucesso!");
                return ResponseEntity.ok(message);
            }
        }

        throw new CustomException("Serviço não encontrado, tente novamente!");
    }
}
