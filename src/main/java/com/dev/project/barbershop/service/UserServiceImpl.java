package com.dev.project.barbershop.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.ErrorWhenSavingOrDeleteException;
import com.dev.project.barbershop.exceptions.NotFoundRecordsException;
import com.dev.project.barbershop.model.Employe;
import com.dev.project.barbershop.payload.EmployeRequestPayload;
import com.dev.project.barbershop.payload.UserRequestPayload;
import com.dev.project.barbershop.repository.EmployeRepository;
import com.dev.project.barbershop.response.UserData;
import com.dev.project.barbershop.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public UserResponse createUser(UserRequestPayload payload) throws CustomException {
        if (payload instanceof EmployeRequestPayload) {
            try {
                String cpf = payload.getCpf();
                Optional<Employe> employe = this.employeRepository.findByCpf(cpf);

                if (employe.isPresent()) {
                    throw new CustomException("Cpf já cadastrado");
                }

                EmployeRequestPayload employePayload = (EmployeRequestPayload) payload;

                Employe newEmploye = new Employe(employePayload);
                String hashPassword = BCrypt.withDefaults().hashToString(12, newEmploye.getPassword().toCharArray());
                newEmploye.setPassword(hashPassword);

                this.employeRepository.save(newEmploye);

                return new UserResponse(newEmploye.getName(), newEmploye.getCpf());
            } catch (ErrorWhenSavingOrDeleteException err) {
                throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao criar", err);
            }
        } else {
            throw new CustomException("Invalid user type");
        }
    }

    @Override
    public List<UserData> getAllUsers(UserRequestPayload payload) throws NotFoundRecordsException {
        if (payload instanceof EmployeRequestPayload) {
            List<UserData> employesList = this.employeRepository.findAll().stream().map(employe -> new UserData(
                    employe.getId(),
                    employe.getCpf(),
                    employe.getName())).toList();

            if (employesList.isEmpty()) {
                throw new NotFoundRecordsException("Nenhum funcionario cadastrado");
            }

            return employesList;
        } else {
            throw new NotFoundRecordsException("Invalid user type");
        }
    }

    @Override
    public UserData getUserById(UserRequestPayload payload, UUID employeId) throws NotFoundRecordsException {
        if (payload instanceof EmployeRequestPayload) {
            Optional<Employe> employe = this.employeRepository.findById(employeId);

            if (employe.isEmpty()) {
                throw new NotFoundRecordsException("Funcionario não encontrado");
            }

            Employe rawEmploye = employe.get();

            return new UserData(rawEmploye.getId(), rawEmploye.getCpf(), rawEmploye.getName());
        } else {
            throw new NotFoundRecordsException("Invalid user type");
        }
    }

    @Override
    public Employe updateEmploye(EmployeRequestPayload payload, UUID employeId) throws NotFoundRecordsException {
        try {
            Optional<Employe> employe = this.employeRepository.findById(employeId);

            if (employe.isPresent()) {
                Employe rawEmploye = employe.get();

                String name = payload.name();
                String cpf = payload.cpf();
                String password = payload.password();

                if (!name.equalsIgnoreCase("")) {
                    rawEmploye.setName(name);
                }

                if (!cpf.equalsIgnoreCase("")) {
                    rawEmploye.setCpf(cpf);
                }

                if (!password.equalsIgnoreCase("")) {
                    rawEmploye.setPassword(password);
                }

                String hashPassword = BCrypt.withDefaults().hashToString(12, rawEmploye.getPassword().toCharArray());
                rawEmploye.setPassword(hashPassword);

                this.employeRepository.save(rawEmploye);

                return rawEmploye;
            }

            return null;

        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao atualizar serviço: ", err);
        }
    }

    @Override
    public Boolean deleteEmploye(UUID employeId) throws ErrorWhenSavingOrDeleteException {
        try {
            Optional<Employe> employeDelete = this.employeRepository.findById(employeId);

            if (employeDelete.isPresent()) {
                this.employeRepository.deleteById(employeId);
                return true;
            }
            return false;
        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao deletar serviço: ", err);
        }
    }
}
