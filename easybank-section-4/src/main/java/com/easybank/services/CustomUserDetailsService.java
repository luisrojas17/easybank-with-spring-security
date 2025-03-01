package com.easybank.services;


import com.easybank.commons.repositories.CustomerRepository;
import com.easybank.commons.repositories.entities.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // we define a supplier method to throw an exception if the user is not found
        CustomerEntity customerEntity =
                customerRepository.findByEmail(username).orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User not found [%s]", username)));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customerEntity.getRole()));

        return new User(customerEntity.getEmail(), customerEntity.getPwd(), authorities);

    }
}
