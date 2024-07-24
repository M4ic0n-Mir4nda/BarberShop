package com.dev.project.barbershop.controller;

import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.NotFoundRecordsException;
import com.dev.project.barbershop.model.Employe;
import com.dev.project.barbershop.payload.EmployeRequestPayload;
import com.dev.project.barbershop.repository.EmployeRepository;
import com.dev.project.barbershop.response.EmployeData;
import com.dev.project.barbershop.response.EmployeResponse;
import com.dev.project.barbershop.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employe")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @PostMapping
    public ResponseEntity<EmployeResponse> createEmploye(@RequestBody EmployeRequestPayload payload) throws CustomException {
        EmployeResponse newEmploye = this.employeService.createEmploye(payload);
        return ResponseEntity.ok(newEmploye);
    }

    @GetMapping("/employes")
    public ResponseEntity<List<EmployeData>> getAllEmployes() throws NotFoundRecordsException {
        List<EmployeData> employesList = this.employeService.getAllEmployes();

        if (employesList.isEmpty()) {
            throw new NotFoundRecordsException("Nenhum funcionario cadastrado");
        }

        return ResponseEntity.ok(employesList);
    }
}
