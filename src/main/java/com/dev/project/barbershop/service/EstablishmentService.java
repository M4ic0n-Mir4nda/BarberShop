package com.dev.project.barbershop.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.repository.AddressRepository;
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
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ViaCepService viaCep;

    public EstablishmentCreateResponse createNewEstablishment(EstablishmentRequestPayload payload) {
        Establishment establishment = saveEstablishment(payload);
        return new EstablishmentCreateResponse(establishment.getId());
    }

    public Optional<Establishment> getEstablishmentByCnpj(String cnpj) {
        return this.establishmentRepository.findEstablishmentByCnpj(cnpj);
    }

    public Optional<Establishment> getEstablishmentDetails(UUID id) {
        return this.establishmentRepository.findById(id);
    }

    private Establishment saveEstablishment(EstablishmentRequestPayload payload) {
        try {
            Establishment newEstablishment = new Establishment(payload);

            String cep = payload.cep();
            Address address = this.addressRepository.findById(cep).orElseGet(() -> {
                Address newAddres = this.viaCep.consultarCep(cep);
                if (newAddres.getCep() == null) {
                    try {
                        throw new CustomException("CEP não encontrado");
                    } catch (CustomException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.addressRepository.save(newAddres);
                return newAddres;
            });
            newEstablishment.setAddress(address);

            String hashPassword = BCrypt.withDefaults().hashToString(12, newEstablishment.getPassword().toCharArray());
            newEstablishment.setPassword(hashPassword);

            this.establishmentRepository.save(newEstablishment);

            return newEstablishment;
        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao criar", err);
        }
    }
}
