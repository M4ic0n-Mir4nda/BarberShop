package com.dev.project.barbershop.model;

import com.dev.project.barbershop.payload.ActivityRequestPayload;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String nameService;

    @Column(nullable = false)
    private Double price;

    @Column(name = "time_service", nullable = false)
    private LocalTime timeService;

    @Column(nullable = false)
    private String description;

    public Activity(ActivityRequestPayload data) {
        this.nameService = data.name_service();
        this.price = data.price();
        this.timeService = LocalTime.parse(data.time_service().format(DateTimeFormatter.ofPattern("HH:mm")));
        this.description = data.description();
    }
}
