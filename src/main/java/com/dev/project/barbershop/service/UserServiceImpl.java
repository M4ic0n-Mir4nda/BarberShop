package com.dev.project.barbershop.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.ErrorWhenSavingOrDeleteException;
import com.dev.project.barbershop.exceptions.NotFoundRecordsException;
import com.dev.project.barbershop.model.Customer;
import com.dev.project.barbershop.model.Employe;
import com.dev.project.barbershop.payload.CustomerRequestPayload;
import com.dev.project.barbershop.payload.EmployeRequestPayload;
import com.dev.project.barbershop.payload.UserRequestPayload;
import com.dev.project.barbershop.repository.CustomerRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserResponse createUser(UserRequestPayload payload) throws CustomException {
        if (payload instanceof EmployeRequestPayload) { // EMPLOYE
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
        } else if (payload instanceof CustomerRequestPayload) { // CUSTOMER
            try {
                String cpf = payload.getCpf();
                Optional<Customer> customer = this.customerRepository.findByCpf(cpf);

                if (customer.isPresent()) {
                    throw new CustomException("Cpf já cadastrado");
                }

                CustomerRequestPayload customerPayload = (CustomerRequestPayload) payload;

                Customer newCustomer = new Customer(customerPayload);

                this.customerRepository.save(newCustomer);

                return new UserResponse(newCustomer.getName(), newCustomer.getCpf());
            } catch (ErrorWhenSavingOrDeleteException err) {
                throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao criar", err);
            }
        } else {
            throw new CustomException("Invalid user type");
        }
    }

    @Override
    public List<UserData> getAllUsers(UserRequestPayload payload) throws NotFoundRecordsException {
        if (payload instanceof EmployeRequestPayload) { // EMPLOYE
            List<UserData> employesList = this.employeRepository.findAll().stream().map(employe -> new UserData(
                    employe.getId(),
                    employe.getCpf(),
                    employe.getName())).toList();

            if (employesList.isEmpty()) {
                throw new NotFoundRecordsException("Nenhum funcionario cadastrado");
            }

            return employesList;
        } else if (payload instanceof CustomerRequestPayload) { // CUSTOMER
            List<UserData> customerList = this.customerRepository.findAll().stream().map(customer -> new UserData(
                    customer.getId(),
                    customer.getCpf(),
                    customer.getName())).toList();

            if (customerList.isEmpty()) {
                throw new NotFoundRecordsException("Nenhum cliente cadastrado");
            }

            return customerList;
        }
        else {
            throw new NotFoundRecordsException("Invalid user type");
        }
    }

    @Override
    public UserData getUserById(UserRequestPayload payload, UUID id) throws CustomException {
        if (payload instanceof EmployeRequestPayload) { // EMPLOYE
            Optional<Employe> employe = this.employeRepository.findById(id);

            if (employe.isEmpty()) {
                throw new CustomException("Funcionario não encontrado");
            }

            Employe rawEmploye = employe.get();

            return new UserData(rawEmploye.getId(), rawEmploye.getCpf(), rawEmploye.getName());
        } else if (payload instanceof CustomerRequestPayload) { // CUSTOMER
            Optional<Customer> customer = this.customerRepository.findById(id);

            if (customer.isEmpty()) {
                throw new CustomException("Cliente não encontrado");
            }

            Customer rawCustomer = customer.get();

            return new UserData(rawCustomer.getId(), rawCustomer.getCpf(), rawCustomer.getName());
        } else {
            throw new CustomException("Invalid payload type");
        }
    }

    // EMPLOYE

    @Override
    public Employe updateEmploye(EmployeRequestPayload payload, UUID employeId) throws CustomException {
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

    // CUSTOMER

    @Override
    public Customer updateCustomer(CustomerRequestPayload payload, UUID id) throws CustomException {
        try {
            Optional<Customer> customer = this.customerRepository.findById(id);

            if (customer.isPresent()) {
                Customer rawCustomer = customer.get();

                String name = payload.name();
                String cpf = payload.cpf();
                String whatsapp = payload.whatsapp();

                if (!name.equalsIgnoreCase("")) {
                    rawCustomer.setName(name);
                }

                if (!cpf.equalsIgnoreCase("")) {
                    rawCustomer.setCpf(cpf);
                }

                if (!whatsapp.equalsIgnoreCase("")) {
                    rawCustomer.setWhatsapp(whatsapp);
                }

                this.customerRepository.save(rawCustomer);

                return rawCustomer;
            }

            return null;

        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao atualizar serviço: ", err);
        }
    }

    @Override
    public Boolean deleteCustomer(UUID customerId) throws ErrorWhenSavingOrDeleteException {
        try {
            Optional<Customer> customerDelete = this.customerRepository.findById(customerId);

            if (customerDelete.isPresent()) {
                this.customerRepository.deleteById(customerId);
                return true;
            }
            return false;
        } catch (ErrorWhenSavingOrDeleteException err) {
            throw new ErrorWhenSavingOrDeleteException("Ocorreu um erro ao deletar serviço: ", err);
        }
    }
}
