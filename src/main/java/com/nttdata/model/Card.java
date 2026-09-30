package com.nttdata.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Card {
    private String id;
    private String customerDocumentNumber;
    private String cardNumber;
    private Double totalBalance;
    private Double usedBalancePEN;
    private Double usedBalanceUSD;
}
