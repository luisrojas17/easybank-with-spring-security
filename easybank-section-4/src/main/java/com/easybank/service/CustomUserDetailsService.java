package com.easybank.service;


import com.easybank.respository.CustomerRepository;
import com.easybank.respository.entity.CustomerEntity;
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
                        new UsernameNotFoundException("User not found" + username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customerEntity.getRole()));

        return new User(customerEntity.getEmail(), customerEntity.getPwd(), authorities);

    }
}
