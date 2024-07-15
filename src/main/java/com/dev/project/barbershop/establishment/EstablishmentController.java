package com.dev.project.barbershop.establishment;

import com.dev.project.barbershop.address.Address;
import com.dev.project.barbershop.address.AddressService;
import com.dev.project.barbershop.exceptions.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/establishment")
public class EstablishmentController {

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/{addressId}/address")
    public ResponseEntity<EstablishmentCreateResponse> createEstablishment(@RequestBody EstablishmentRequestPayload payload,
                                                                           @PathVariable UUID addressId) {
            String cnpj = payload.cnpj().trim();

            if (cnpj.isEmpty()) {
                throw new CustomException("Informe um CNPJ");
            } else if (cnpj.length() != 18) {
                throw new CustomException("Informe um CNPJ existente");
            }

            Optional<Establishment> establishment = this.establishmentService.getEstablishmentByCnpj(cnpj);

            if (establishment.isPresent()) {
                throw new CustomException("Não foi possivel criar estabelecimento! CNPJ já cadastrado!");
            }

            Optional<Address> address = this.addressService.getEstablishmentAddress(addressId);

            if (address.isEmpty()) {
                throw new CustomException("endereço ID não encontrado");
            }

            EstablishmentCreateResponse establishmenCreatedId = this.establishmentService.createNewEstablishment(payload,
                    address);

            return ResponseEntity.ok(establishmenCreatedId);
    }

    @GetMapping("/{establishmentId}")
    public ResponseEntity<Establishment> getEstablishmentDetails(@PathVariable UUID establishmentId) {
        Optional<Establishment> establishment = this.establishmentService.getEstablishmentDetails(establishmentId);

        if (establishment.isEmpty()) {
            throw new CustomException("estabelecimento ID não encontrado");
        }

        return establishment.map(ResponseEntity::ok).orElseGet(() -> null);
    }

    @GetMapping("/{establishmentId}/activity/{}")
    public void getActivity() {

    }

}
