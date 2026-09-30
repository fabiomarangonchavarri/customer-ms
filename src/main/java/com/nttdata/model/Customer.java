package com.nttdata.model;

import lombok.Data;

@Data
public class Customer {
    private String id;
    private String documentNumber;
    private String passwordHash;
    private String name;
    private String email;
    private String status;
}
