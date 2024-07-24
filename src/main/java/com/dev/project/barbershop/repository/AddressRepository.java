package com.dev.project.barbershop.repository;


import com.dev.project.barbershop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
