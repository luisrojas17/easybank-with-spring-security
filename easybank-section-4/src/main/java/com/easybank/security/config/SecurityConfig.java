package com.easybank.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
    static final String[] PERMITTED_PATHS = {"/contact", "/notices", "/error"};

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
        http.authorizeHttpRequests((requests) ->
                requests.requestMatchers(AUTHENTICATED_PERMITTED_PATHS).authenticated()
                        .requestMatchers(PERMITTED_PATHS).permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        return http.build();
    }

    /**
     * This method builds in-memory user details service. Specifically an in-memory implementation
     * of UserDetailsService.
     *
     * @return an instance of InMemoryUserDetailsManager.
     *
     * @see UserDetailsService
     * @see User
     * @see InMemoryUserDetailsManager
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
