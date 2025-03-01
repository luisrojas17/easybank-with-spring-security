package com.easybank.controllers;

import com.easybank.controllers.model.Customer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.easybank.commons.constants.APIConstants.CUSTOMERS_PATH;

@RequestMapping(CUSTOMERS_PATH)
public interface CustomerController {

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> register(@RequestBody Customer customer);
}
