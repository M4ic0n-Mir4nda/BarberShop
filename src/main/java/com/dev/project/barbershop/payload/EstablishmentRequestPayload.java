package com.dev.project.barbershop.payload;

public record EstablishmentRequestPayload(String email, String password, String name, String cnpj, String whatsapp, String cep) {
}
