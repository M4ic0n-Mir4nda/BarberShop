package com.dev.project.barbershop.establishment;

import java.util.UUID;

public record EstablishmentRequestPayload(String email, String password, String name, String cnpj, String whatsapp) {
}
