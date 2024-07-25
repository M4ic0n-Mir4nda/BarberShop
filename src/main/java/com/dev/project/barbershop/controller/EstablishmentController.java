package com.dev.project.barbershop.controller;

import com.dev.project.barbershop.model.Establishment;
import com.dev.project.barbershop.response.EstablishmentCreateResponse;
import com.dev.project.barbershop.payload.EstablishmentRequestPayload;
import com.dev.project.barbershop.service.EstablishmentService;
import com.dev.project.barbershop.exceptions.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/establishment")
@Tag(name = "Controller Estabelecimento")
public class EstablishmentController {

    @Autowired
    private EstablishmentService establishmentService;

    @Operation(summary = "Criar um novo Estabelecimento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação de estebelecimento realizado com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            // @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a criação de estabelecimento")
    })
    // consumes = MediaType.MULTIPART_FORM_DATA_VALUE > permite que carregue documentos
    @PostMapping
    public ResponseEntity<EstablishmentCreateResponse> createEstablishment(@RequestBody EstablishmentRequestPayload payload) throws CustomException {
            String cnpj = payload.cnpj().trim();

            if (cnpj.isEmpty()) {
                throw new CustomException("Informe um CNPJ");
            } else if (cnpj.length() != 18) {
                throw new CustomException("Informe um CNPJ existente");
            }

            Optional<Establishment> establishment = this.establishmentService.getEstablishmentByCnpj(cnpj);

            if (establishment.isPresent()) {
                Establishment cnpjEstablishment = establishment.get();
                throw new CustomException("Não foi possivel criar estabelecimento: " + cnpjEstablishment.getCnpj() + " já cadastrado!");
            }

            EstablishmentCreateResponse establishmenCreatedId = this.establishmentService.createNewEstablishment(payload);

            return ResponseEntity.ok(establishmenCreatedId);
    }

    @GetMapping("/{establishmentId}")
    public ResponseEntity<Establishment> getEstablishmentDetails(@PathVariable UUID establishmentId) throws CustomException {
        Optional<Establishment> establishment = this.establishmentService.getEstablishmentDetails(establishmentId);

        if (establishment.isEmpty()) {
            throw new CustomException("estabelecimento não encontrado");
        }

        return establishment.map(ResponseEntity::ok).orElseGet(() -> null);
    }
}
