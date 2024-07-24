package com.dev.project.barbershop.repository;

import com.dev.project.barbershop.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

}
