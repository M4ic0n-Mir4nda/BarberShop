package com.dev.project.barbershop.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping
    public ResponseEntity<AddressCreateResponse> createAddress(@RequestBody AddressRequestPayload payload) {
        Address newAddres = new Address(payload);

        this.addressRepository.save(newAddres);

        return ResponseEntity.ok(new AddressCreateResponse(newAddres.getId()));
    }
}
