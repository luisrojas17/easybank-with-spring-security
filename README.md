# Easy Bank Application

This application expose an API with basic bank's functionality. Also, the security is implemented with Spring Boot and Spring Security. Inside this repository you can find different modules where each module is implemented with a specific Spring  Security's feature. 

To start the application from command line you can use next commands:

Using Java jar command:

    ** java -jar target/easybank-section-4-1.0.0.jar

Using Maven:

    ** mvn spring-boot:run

However, it is necessary to add next plugin to your pom.xml file:

```
...
<build>
    <plugins>
    ...
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    ..
    </plugins>
</build>
...
```
