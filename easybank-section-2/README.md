# Easy Bank Application
This application uses Spring Boot and Spring Security. Specifically shows how to change the default Spring Security
configuration to protect only some of the application resources.

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


