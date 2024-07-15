package com.dev.project.barbershop.establishment;

import com.dev.project.barbershop.address.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EstablishmentService {

    @Autowired
    EstablishmentRepository establishmentRepository;

    public EstablishmentCreateResponse createNewEstablishment(EstablishmentRequestPayload payload, Optional<Address> address) {
        Establishment newEstablishment = new Establishment(payload, address.orElse(null));

        this.establishmentRepository.save(newEstablishment);

        return new EstablishmentCreateResponse(newEstablishment.getEstablishmentId());
    }

    public Optional<Establishment> getEstablishmentByCnpj(String cnpj) {
        Optional<Establishment> establishment = Optional.ofNullable(this.establishmentRepository.findEstablishmentByCnpj(cnpj));
        return establishment;
    }

    public Optional<Establishment> getEstablishmentDetails(UUID id) {
        return this.establishmentRepository.findById(id);
    }
}
