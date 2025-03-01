package com.easybank.controllers;

import com.easybank.controllers.model.Customer;
import com.easybank.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<String> register(final Customer customer) {

        log.info("Processing request to register user: {}", customer);

        long userId = customerService.register(customer);

        log.info("Request processed. The u user id created is: [{}].", userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                String.format("User created with id: %s", userId));
    }
}
