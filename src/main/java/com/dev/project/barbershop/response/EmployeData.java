package com.dev.project.barbershop.response;

import java.util.UUID;

public record EmployeData(UUID id, String cpf, String name, String password) {
}
