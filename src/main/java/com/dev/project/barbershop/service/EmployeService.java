package com.dev.project.barbershop.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.ErrorWhenSavingOrDeleteException;
import com.dev.project.barbershop.model.Employe;
import com.dev.project.barbershop.payload.EmployeRequestPayload;
import com.dev.project.barbershop.repository.EmployeRepository;
import com.dev.project.barbershop.response.EmployeData;
import com.dev.project.barbershop.response.EmployeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    public EmployeResponse createEmploye(EmployeRequestPayload payload) throws CustomException {
        try {
            String cpf = payload.cpf();
            Optional<Employe> employe = this.employeRepository.findByCpf(cpf);

            if (employe.isPresent()) {
                throw new CustomException("Cpf já cadastrado");
            }

            Employe newEmploye = new Employe(payload);
            String hashPassword = BCrypt.withDefaults().hashToString(12, newEmploye.getPassword().toCharArray());
            newEmploye.setPassword(hashPassword);

            this.employeRepository.save(newEmploye);

            return new EmployeResponse(newEmploye.getName(), newEmploye.getCpf());
        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao criar", err);
        }
    }

    public List<EmployeData> getAllEmployes() {
        return this.employeRepository.findAll().stream().map(employe -> new EmployeData(
                employe.getId(),
                employe.getCpf(),
                employe.getName())).toList();
    }

}
