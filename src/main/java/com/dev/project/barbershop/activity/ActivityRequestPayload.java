package com.dev.project.barbershop.activity;

import java.time.LocalTime;

public record ActivityRequestPayload(String name_service, Double price, LocalTime time_service, String description) {
}
