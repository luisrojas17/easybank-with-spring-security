package com.easybank.commons.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * This class overrides the default Spring Security configuration since the default behaviour
 * is to protect all the application.
 *
 * @see org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration
 */
@Configuration
public class SecurityConfig {

    static final String[] AUTHENTICATED_PERMITTED_PATHS = {"/myAccount", "/myBalance", "/myCards", "/myLoans"};
    static final String[] PERMITTED_PATHS = {"/contact", "/notices", "/error", "/api/v1/customers/register"};

    /**
     * This method builds a custom instance of the security filter chain.
     * .
     * @param http an instance of HttpSecurity. It represents the security configuration for all request incoming.
     * @return a custom instance of SecurityFilterChain.
     * @throws Exception an instance of Exception.
     *
     * @see HttpSecurity
     * @see SecurityFilterChain
     */
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());

        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers(AUTHENTICATED_PERMITTED_PATHS).authenticated()
                                .requestMatchers(PERMITTED_PATHS).permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        return http.build();
    }

    /**
     * This method builds jdbc manager user details service implementation.
     * Specifically is an implementation of UserDetailsService.
     *
     * @param dataSource an instance of DataSource to connect to the database.
     *                   This is built by Spring boot according to database configuration
     *                  specified in application.properties
     * @return an instance of JdbcUserDetailsManager which is an implementation of UserDetailsService.
     * Using this implementation if you want to authenticate users against a database.
     *
     * @see JdbcUserDetailsManager
     * @see UserDetailsService
     * @see User
     */
    /*@Bean
    public UserDetailsService buildUserDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }*/

    @Bean
    public PasswordEncoder buildPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
