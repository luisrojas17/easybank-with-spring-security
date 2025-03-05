# Easy Bank Application
This application uses Spring Boot and Spring Security. Specifically shows default Spring Security configuration 
to protect all the application resources. It also shows how to change the default username and password and
how to work some filters, managers and providers.

## Default Spring Security configuration
By default, Spring protects all the application. The code is written in Spring Security Configuration class:

    ** org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration:defaultSecurityFilterChain

## Default user in Spring Security configuration
To consume any resource in application with Spring Security you will have to provide the credentials
in order to authenticate the user into application. The user that you have to use is: "user" and the password
is generated random way. Take a look in the console when the application has stated and you will see a message
like this:

    ** Using generated security password: bca93b01-6e24-4757-a31e-b9b5b4ecae4b

The default username and password are defined in the class:
    ** org.springframework.boot.autoconfigure.security.SecurityProperties

So, you could use that password to authenticate in the application. 

On the other hand, if you want to change the default userName and password you will have to define those values 
through next properties in application.properties or application.yml file:

    ** spring.security.user.name=new_user_name_value
    ** spring.security.user.password=new_user_password_value

    ** See: [You can check this link to learn more about properties](https://docs.spring.io/spring-boot/appendix/application-properties/index.html) 

## Spring Security flow
1. Spring security filters
A series of Spring Security filters intercept each request and work together to identify if authentication 
is required or not. If authentication is required, accordingly navigate the user to login page or use 
the existing details stored during initial authentication

       ** See: org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
       ** See: org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

2. Authentication
Filters like UsernamePasswordAuthenticationFilter will extract username/password from HTTP request & prepare 
Authentication type object. Because authentication is the core standard of storing authenticated user details
inside Spring Security Framework. See next filters:

   1. **AuthorizationFilter
   2. **DefaultLoginPageGeneratingFilter
   3. **AbstractAuthenticationProcessingFilter
      1. UsernamePasswordAuthenticationFilter: Extracts the username and password
   4. **UsernamePasswordAuthenticationFilter
      This filter is used when the application is using default security configuration and the form login security
      is enabled.
   
      And returning an instance of org.springframework.security.web.authentication.Authentication, specifically 
      org.springframework.security.authentication.UsernamePasswordAuthenticationToken      

3. Authentication manager
Once received the request from filter, it delegates the validating of the user details to the authentication 
providers available. Since there can be multiple providers inside the application, it is responsibility of the 
AuthenticationManager to manage all the authentication providers available. In simple words, the authentication
manager takes the responsibility for authentication.

       ** See: org.springframework.security.authentication.AuthenticationManager:authenticate
    
4. Authentication provider
AuthenticationProvider has all the core logic of validating user details for authentication.

       ** See: org.springframework.security.authentication.ProviderManager:authenticate
               Implements org.springframework.security.authentication.AuthenticationManager

               Calls implementation of org.springframework.security.authentication.AuthenticationProvider:authenticate

       ** See: org.springframework.security.authentication.AuthenticationProvider:authenticate
       ** See: org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider:authenticate
               Implements org.springframework.security.authentication.AuthenticationProvider

               Calls implementation of org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.retrieveUser

               Returns an object of org.springframework.security.authentication.UsernamePasswordAuthenticationToken

       ** See: org.springframework.security.authentication.dao.DaoAuthenticationProvider:retrieveUser
               Extends org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
     
               Calls org.springframework.security.core.userdetails.UserDetailsService:loadUserByUsername 
       

5. Load user details
UserDetailsManager/UserDetailsService helps in retrieving, creating, updating, deleting the user details from
the database. Spring Security provides multiple implementations of UserDetailsManager and UserDetailsService.

       
       ** See: org.springframework.security.core.userdetails.UserDetailsService:loadUserByUsername    
       ** See: org.springframework.security.core.userdetails.InMemoryUserDetailsManager:loadUserByUsername
               Implements org.springframework.security.core.userdetails.UserDetailsService
               Implements org.springframework.security.provisioningUserDetailsManager

               Usage:
                     org.springframework.security.core.userdetails.UserDetails
                     org.springframework.security.core.userdetails.User
                     org.springframework.security.core.userdetails.UsernameNotFoundException


6. Password encoder
Service interface that helps in encoding & hashing passwords. Otherwise we may have to live with plain text passwords.

       ** See: org.springframework.security.crypto.password.PasswordEncoder

       ** See: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
       ** See: org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
       ** See: org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
       ** See: org.springframework.security.crypto.password.StandardPasswordEncoder

7. SecurityContext
Once the request has been authenticated the authentication will usually be stored in a thread-local SecurityContext 
managed by the SecurityContextHolder. This helps during the upcoming request from the same user.







