package com.nttdata.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Account {
    private String id;
    private String customerDocumentNumber;
    private String accountNumber;
    private String accountType;
    private String currency;
    private Double balance;
    private OffsetDateTime lastUpdated;
}
