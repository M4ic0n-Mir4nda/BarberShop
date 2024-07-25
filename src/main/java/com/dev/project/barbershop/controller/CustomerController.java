package com.dev.project.barbershop.controller;

import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.NotFoundRecordsException;
import com.dev.project.barbershop.model.Customer;
import com.dev.project.barbershop.payload.CustomerRequestPayload;
import com.dev.project.barbershop.response.UserData;
import com.dev.project.barbershop.response.UserResponse;
import com.dev.project.barbershop.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserResponse> createCustomer(@RequestBody CustomerRequestPayload payload) throws CustomException {
        UserResponse newCustomer = this.userService.createUser(payload);
        return ResponseEntity.ok(newCustomer);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<UserData>> getAllCustomers(@RequestBody CustomerRequestPayload payload) throws NotFoundRecordsException {
        List<UserData> customerList = this.userService.getAllUsers(payload);
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<UserData> getCustomereById(@PathVariable UUID customerId) throws CustomException {
        CustomerRequestPayload payload = new CustomerRequestPayload("", "", "");
        UserData customer = this.userService.getUserById(payload, customerId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Optional<Customer>> updateCustomer(@PathVariable UUID customerId,
                                                           @RequestBody CustomerRequestPayload payload) throws CustomException {
        Optional<Customer> customer = Optional.ofNullable(this.userService.updateCustomer(payload, customerId));

        if (customer.isEmpty()) {
            throw new CustomException("Cliente não encontrado");
        }
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable UUID customerId) throws CustomException {
        Boolean customerDelete = this.userService.deleteCustomer(customerId);

        if (customerDelete) {
            Map<String, String> message = new HashMap<>();
            message.put("message", "Cliente deletado com sucesso!");
            return ResponseEntity.ok(message);
        }

        throw new CustomException("Cliente não encontrado");
    }
}
