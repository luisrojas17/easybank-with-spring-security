# Easy Bank Application
This application uses Spring Boot and Spring Security. Specifically show how to create new users in memory and
avoid using the default user defined in:

    ** org.springframework.boot.autoconfigure.security.SecurityProperties

## Default Spring Security configuration
By default, Spring protects all the application. The code is written in Spring Security Configuration class:

    ** org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration:defaultSecurityFilterChain

If you want to change default security configuration you must override that class' method in your project.

    ** See: com.easybank.security.config.SecurityConfig:defaultSecurityFilterChain

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

## Create custom users in memory
To create a new memory users you have to provide a method that returns an instance of
org.springframework.security.core.userdetails.User and pass the list of users to the constructor of 
org.springframework.security.core.userdetails.UserDetailsService.

    ** See: com.easybank.security.config.SecurityConfig:buildUserDetailsService

## Password encoder
In addition, if you do not provide an instance of org.springframework.security.crypto.password.PasswordEncoder 
you should set the password with prefix '{noop}anyPasswordString'. 

    ** Note: The encoder {noop} -> org.springframework.security.crypto.password.NoOpPasswordEncoder is deprecated.

However, you can create an instance of org.springframework.security.crypto.password.PasswordEncoder through next method:
 
    ** PasswordEncoderFactories.createDelegatingPasswordEncoder()

Which will return and instance of org.springframework.security.crypto.password.PasswordEncoder which wraps multiple
password encoders. By default, password encoder it will be org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder 

    ** Please use next URL in order to encode your password: https://bcrypt-generator.com

## Check if the password has compromised
To check if the password has compromised you can define an implementation of  
**org.springframework.security.authentication.password.CompromisedPasswordChecker** specifically an instance of
**org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker**. This implementation
uses the method "check" to validate if the password provided has been compromised. The source where all passwords are
validated is: [Have I Been Pwned REST API](https://haveibeenpwned.com/API/v3#PwnedPasswords).

    ** Note: This feature is available only in Spring Security 6.X.X version or higher. 


 

    


