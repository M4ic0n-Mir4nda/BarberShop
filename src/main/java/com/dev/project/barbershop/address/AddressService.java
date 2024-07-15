package com.dev.project.barbershop.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Optional<Address> getEstablishmentAddress(UUID id) {
        return this.addressRepository.findById(id);
    }
}
