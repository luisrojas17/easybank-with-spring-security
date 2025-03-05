package com.easybank.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

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
    @Bean
    public UserDetailsService buildUserDetailsService() {

        // If you do not provide a PasswordEncoder you should set the password with prefix '{noop}anyPasswordString'
        // Also you can check all the available password encoders with
        // PasswordEncoderFactories.createDelegatingPasswordEncoder()
        // Please use next URL in order to encode your password: https://bcrypt-generator.com
        UserDetails admin =
                //User.withUsername("adminUser").password("{noop}adminUserPassword").authorities("admin").build();
                User.withUsername("adminUser").password("{bcrypt}$2a$12$Wl76XDmqT/z7227sQhim5eDuE8p150gyLQuzuztj4vdtPcrtFQGUq").authorities("admin").build();

        UserDetails user =
                User.withUsername("user").password("{noop}userPassword").authorities("read").build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder buildPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     *
     * @return
     */
    @Bean
    public CompromisedPasswordChecker buildCompromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
