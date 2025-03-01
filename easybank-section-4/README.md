# Easy Bank Application
This application uses Spring Boot and Spring Security. Specifically show how to create new users in database and
avoid using the default user defined in:

    ** org.springframework.boot.autoconfigure.security.SecurityProperties

## Default Spring Security Configuration
By default, Spring protects all the application. The code is written in Spring Security Configuration class:

    ** org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration:defaultSecurityFilterChain

If you want to change default security configuration you must override that class' method in your project.

    ** See: com.easybank.commons.security.config.SecurityConfig:defaultSecurityFilterChain

## Default User in Spring Security Configuration
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

## Create Custom Users in Memory
To create a new memory users you have to provide a method that returns an instance of
org.springframework.security.core.userdetails.User and pass the list of users to the constructor of 
org.springframework.security.core.userdetails.UserDetailsService.

    ** See: com.easybank.commons.security.config.SecurityConfig:buildUserDetailsService

## Password Encoder
In addition, if you do not provide an instance of org.springframework.security.crypto.password.PasswordEncoder 
you should set the password with prefix '{noop}anyPasswordString'. 

    ** Note: The encoder {noop} -> org.springframework.security.crypto.password.NoOpPasswordEncoder is deprecated.

However, you can create an instance of org.springframework.security.crypto.password.PasswordEncoder through next method:
 
    ** PasswordEncoderFactories.createDelegatingPasswordEncoder()

Which will return and instance of org.springframework.security.crypto.password.PasswordEncoder which wraps multiple
password encoders. By default, password encoder it will be org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder 

    ** Please use next URL in order to encode your password: https://bcrypt-generator.com

## Check if the Password Has Compromised
To check if the password has compromised you can define an implementation of  
**org.springframework.security.authentication.password.CompromisedPasswordChecker** specifically an instance of
**org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker**. This implementation
uses the method "check" to validate if the password provided has been compromised. The source where all passwords are
validated is: [Have I Been Pwned REST API](https://haveibeenpwned.com/API/v3#PwnedPasswords).

    ** Note: This feature is available only in Spring Security 6.X.X version or higher. 

## Create Custom Users in Database
To enable the creation of users in database you have to add next dependency in pom.xml file:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<dependency>
    <groupId>com.mysql</groupId>
	<artifactId>mysql-connector-j</artifactId>
	<scope>runtime</scope>
</dependency>
```

### org.springframework.security.provisioning.JdbcUserDetailsManager
This class is a specialization of org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl which provides 
CRUD operations for users and groups. However, if you use JdbcUserDetailsManager class to get the user details, 
you will be to force to use the default schema structure which is defined in class 
org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl which uses JdbcTemplate to retrieve user details 
from a database. In this class is defined the schema structure that you will have to create in the database. 
The tables to be created are:

    ** users
    ** authorities
    ** groups

To get more details about the schema to be created you can check next file:

    ** org/springframework/security/core/userdetails/jdbc/users.ddl

    Note: This file is located in the jar file: spring-security-core-6.3.6.jar

To use org.springframework.security.provisioning.JdbcUserDetailsManager you will have to indicate to Spring Security
that you want to use this class. That is why, you will have to define a bean creation in spring configuration class. 
For example:
```
@Configuration
public class SecurityConfig {

....

@Bean
public UserDetailsService buildUserDetailsService(DataSource dataSource) {
return new JdbcUserDetailsManager(dataSource);
}

.....

}
```

### Customize Database Schema to Create Users in Database
If you want to use your own schema (tables) to authenticate users you will have to implement your own
org.springframework.security.core.userdetails.UserDetailsService class. See next class:

    ** com.easybank.services.CustomUserDetailsService

And remove any AuthenticationManager bean definition provided by Spring Security defined in your custom
security config class. See next class:

    ** com.easybank.commons.security.config.SecurityConfig

### Enable JPA Entities and Repositories
To enable JPA entities and repositories you have to add next dependency in pom.xml file:

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
After that, you will have to add next annotations to enable auto-scanning entities and repositories in your main
spring boot class:

    ** @EnableJpaRepositories("com.easybank.repository")
    ** @EntityScan("com.easybank.entity")

However, if you have all your repositories and entities classes inside the main package, for example in a subpackage,
those annotations are not necessary.

## Disable Spring Security CSRF Configuration
By default, Spring Security does not allow to consume any API through HTTP POST, PUT and DELETE methods without 
authenticate the request. If you consume the API through any of these methods Spring security will require 
to authenticate the request. On the other hand, if you need to expose and consuming the API without authenticate 
the request you will have to disable CSFR protection provided by Spring Security through
org.springframework.security.config.annotation.web.builders.HttpSecurity:csrf. See next configuration class:

    ** com.easybank.commons.security.config.SecurityConfig:defaultSecurityFilterChain

The unique way to consume an API without authenticate the request is through HTTP GET method.

## Install MySQL through Docker
To install MySQL in Docker you can use next command:

    ** docker run --name easybank-server -e MYSQL_USER=easybank-user -e MYSQL_PASSWORD=easybank-password@2025 -e MYSQL_ROOT_PASSWORD=root@2025 -e MYSQL_DATABASE=easybank -p 3306:3306 -v $HOME/mysql/data:/var/lib/mysql -d mysql

Where
    
    ** --name easybank-server                       -> To specify the container's name
    ** -e MYSQL_USER=easybank-user                  -> To specify the user name for database
    ** -e MYSQL_PASSWORD=easybank-password@2025     -> To specify the password for database
    ** -e MYSQL_ROOT_PASSWORD=root@2025             -> To specify the root user password
    ** -e MYSQL_DATABASE=easybank                   -> To specify the database
    ** -p 3306:3306                                 -> To expose the container's port 3306 to the host port 3306
    ** -v $HOME/mysql/data:/var/lib/mysql           -> To specify the directory where the data will be stored
    ** -d                                           -> To run the container in background
    ** mysql                                        -> To specify the image name and version

If you have already created the container you can start it with next command:

    ** docker start easybank-server

    where easybank-server is the container's name.

If you get an error like this:

    chown: changing ownership of '/var/lib/mysql/mysql.sock': Operation not permitted

    You will have to remove the file $HOME/mysql/data/mysql.sock

To stop the container you can use next command:

    ** docker ps

    Copy the container id and execute next command:

    ** docker stop container_id

To connect to database you can connect to container and use next command:

    ** docker exec -it easybank-server mysql -u easybank-user -peasybank-password@2025

If you prefer use a graphical client you can use mysql-workbench.
https://hub.docker.com/r/jdecool/mysql-workbench
    ** docker pull jdecool/mysql-workbench

    ** docker run --name mysql-workbench -p 8080:8080 -d jdecool/mysql-workbench

## Install MySQL through Docker Compose
If you prefer use docker-compose you can use next command:

    ** docker compose up -d

    Note: You must be in the same directory where the docker-compose.yaml file is located

    See the src/main/resources/docker/docker-compose.yaml file for more information.

To connect to database you can use next command:

    ** docker compose run mysql-client mysql -h easybank-server-1 -u easybank-user -peasybank-password@2025
    ** docker compose run mysql-client mysql -h easybank-server-1 -u root -proot@2025

or

    ** docker exec -it easybank-server-1 mysql -u easybank-user -peasybank-password@2025
    ** docker exec -it easybank-server-1 mysql -u root -proot@2025

To stop all containers starter with docker compose you can use next command:

    ** docker compose stop

To remove all containers started with docker compose you can use next command:

    ** docker compose down



Note: Check next documentation
    **https://hub.docker.com/_/mysql
    **https://hub.docker.com/r/linuxserver/mysql-workbench
    **https://www.warp.dev/blog/how-to-run-mysql-in-docker/ for more information.

 

    


