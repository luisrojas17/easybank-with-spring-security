package com.easybank.commons.facades;

import com.easybank.commons.facades.mappers.CustomerMapper;
import com.easybank.commons.repositories.CustomerRepository;
import com.easybank.commons.repositories.entities.CustomerEntity;
import com.easybank.controllers.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomerFacadeImpl implements CustomerFacade {

    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Long register(final Customer customer) {

        // TODO: Add this feature to mapper class
        String encodedPassword = passwordEncoder.encode(customer.getPwd());
        customer.setPwd(encodedPassword);

        CustomerEntity customerEntity =
                customerMapper.mapToEntity(customer);

        customerEntity = customerRepository.save(customerEntity);

        if(Objects.isNull(customerEntity.getId())) {
            throw new RuntimeException("User could not be created.");
        }

        return customerEntity.getId();

    }
}
