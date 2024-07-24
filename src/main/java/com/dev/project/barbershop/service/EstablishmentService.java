package com.dev.project.barbershop.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dev.project.barbershop.response.EstablishmentCreateResponse;
import com.dev.project.barbershop.payload.EstablishmentRequestPayload;
import com.dev.project.barbershop.model.Address;
import com.dev.project.barbershop.exceptions.ErrorWhenSavingOrDeleteException;
import com.dev.project.barbershop.model.Establishment;
import com.dev.project.barbershop.repository.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EstablishmentService {

    @Autowired
    EstablishmentRepository establishmentRepository;

    public EstablishmentCreateResponse createNewEstablishment(EstablishmentRequestPayload payload, Optional<Address> address) {
        try {
            Establishment newEstablishment = new Establishment(payload, address.orElse(null));
            String hashPassword = BCrypt.withDefaults().hashToString(12, newEstablishment.getPassword().toCharArray());
            newEstablishment.setPassword(hashPassword);

            this.establishmentRepository.save(newEstablishment);

            return new EstablishmentCreateResponse(newEstablishment.getId());
        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao criar", err);
        }
    }

    public Optional<Establishment> getEstablishmentByCnpj(String cnpj) {
        Optional<Establishment> establishment = Optional.ofNullable(this.establishmentRepository.findEstablishmentByCnpj(cnpj));
        return establishment;
    }

    public Optional<Establishment> getEstablishmentDetails(UUID id) {
        return this.establishmentRepository.findById(id);
    }
}
