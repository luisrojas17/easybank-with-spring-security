package com.easybank.controllers.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

    private String email;

    private String pwd;

    private String role;
}
