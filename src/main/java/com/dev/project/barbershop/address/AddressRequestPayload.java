package com.dev.project.barbershop.address;

public record AddressRequestPayload(String street, int number, String complement, String cep, String city, String uf) {
}
