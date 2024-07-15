package com.dev.project.barbershop.address;

import java.util.UUID;

public record AddressData(UUID id, String street, int number, String complement, String cep, String city, String uf) {
}
