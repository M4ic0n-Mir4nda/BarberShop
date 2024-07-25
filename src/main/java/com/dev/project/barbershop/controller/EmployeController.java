package com.dev.project.barbershop.controller;

import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.NotFoundRecordsException;
import com.dev.project.barbershop.model.Employe;
import com.dev.project.barbershop.payload.EmployeRequestPayload;
import com.dev.project.barbershop.response.UserData;
import com.dev.project.barbershop.response.UserResponse;
import com.dev.project.barbershop.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("employe")
public class EmployeController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserResponse> createEmploye(@RequestBody EmployeRequestPayload payload) throws CustomException {
        UserResponse newEmploye = this.userService.createUser(payload);
        return ResponseEntity.ok(newEmploye);
    }

    @GetMapping("/employes")
    public ResponseEntity<List<UserData>> getAllEmployes(@RequestBody EmployeRequestPayload payload) throws NotFoundRecordsException {
        List<UserData> employesList = this.userService.getAllUsers(payload);
        return ResponseEntity.ok(employesList);
    }

    @GetMapping("/{employeId}")
    public ResponseEntity<UserData> getEmployeById(@PathVariable UUID employeId) throws CustomException {
        EmployeRequestPayload payload = new EmployeRequestPayload("", "", "");
        UserData employe = this.userService.getUserById(payload, employeId);
        return ResponseEntity.ok(employe);
    }

    @PutMapping("/{employeId}")
    public ResponseEntity<Optional<Employe>> updateEmploye(@PathVariable UUID employeId,
                                                           @RequestBody EmployeRequestPayload payload) throws CustomException {
        Optional<Employe> employe = Optional.ofNullable(this.userService.updateEmploye(payload, employeId));

        if (employe.isEmpty()) {
            throw new CustomException("Funcionario não encontrado");
        }
        return ResponseEntity.ok(employe);
    }

    @DeleteMapping("/{employeId}")
    public ResponseEntity<Map<String, String>> deleteEmploye(@PathVariable UUID employeId) throws CustomException {
        Boolean employeDelete = this.userService.deleteEmploye(employeId);

        if (employeDelete) {
            Map<String, String> message = new HashMap<>();
            message.put("message", "Funcionario deletado com sucesso!");
            return ResponseEntity.ok(message);
        }

        throw new CustomException("Funcionario não encontrado");
    }
}
