package com.dev.project.barbershop.service;

import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.ErrorWhenSavingOrDeleteException;
import com.dev.project.barbershop.exceptions.NotFoundRecordsException;
import com.dev.project.barbershop.model.Customer;
import com.dev.project.barbershop.model.Employe;
import com.dev.project.barbershop.payload.CustomerRequestPayload;
import com.dev.project.barbershop.payload.EmployeRequestPayload;
import com.dev.project.barbershop.payload.UserRequestPayload;
import com.dev.project.barbershop.response.UserData;
import com.dev.project.barbershop.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserResponse createUser(UserRequestPayload payload) throws CustomException;

    List<UserData> getAllUsers(UserRequestPayload payload) throws NotFoundRecordsException;

    UserData getUserById(UserRequestPayload payload, UUID id) throws CustomException;

    // EMPLOYE

    Employe updateEmploye(EmployeRequestPayload payload, UUID employeId) throws CustomException;

    Boolean deleteEmploye(UUID employeId) throws ErrorWhenSavingOrDeleteException;

    // CUSTOMER

    Customer updateCustomer(CustomerRequestPayload payload, UUID customerId) throws CustomException;

    Boolean deleteCustomer(UUID customerId) throws ErrorWhenSavingOrDeleteException;
}
