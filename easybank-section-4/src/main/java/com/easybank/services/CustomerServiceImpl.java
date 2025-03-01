package com.easybank.services;

import com.easybank.commons.facades.CustomerFacade;
import com.easybank.controllers.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerFacade customerFacade;

    public Long register(final Customer customer) {

        return customerFacade.register(customer);
    }

}
