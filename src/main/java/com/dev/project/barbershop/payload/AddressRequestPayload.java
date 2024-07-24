package com.dev.project.barbershop.payload;

public record AddressRequestPayload(String street, int number, String complement, String cep, String city, String uf) {
}
