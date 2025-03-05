package com.easybank.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

        //http.formLogin(withDefaults());
        //http.httpBasic(withDefaults());

        // If you want to disable the form logging you can do next.
        // However, the application will use BasicAuthentication form login and next filters will be executed:
        // org.springframework.security.web.authentication.www.BasicAuthenticationFilter
        // org.springframework.security.web.authentication.www.BasicAuthenticationConverter
        http.formLogin(AbstractHttpConfigurer::disable);

        // On the other hand, if you disable httpBasic, the application will not be able to logging since
        // there will not be any component to authenticate each request.
        http.httpBasic(withDefaults());

        return http.build();
    }
}
